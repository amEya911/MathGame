package eu.tutorials.mathgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.datasource.remote.AnalyticsLogger
import eu.tutorials.mathgame.data.datasource.remote.LogEvents
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.Answer
import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.data.model.Option
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.navigation.popBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val analyticsLogger: AnalyticsLogger,
) : ViewModel() {

    val config get() = remoteConfig

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private val mutex = Mutex()

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.NavigateBackStack -> {
                analyticsLogger.log(LogEvents.GAME_COMPLETED)
                event.navigator.popBack()
                onEvent(GameEvent.OnReset)
            }

            is GameEvent.ChangeCircleRadius -> {
                _gameState.value = _gameState.value.copy(circleRadius = event.newRadius)
            }

            is GameEvent.ChangeSelectedButtonRect -> {
                _gameState.value = _gameState.value.copy(selectedButtonRect = event.newRect)
            }

            GameEvent.OnNextQuestion -> {
                nextQuestion()
            }

            is GameEvent.OnOptionButtonClicked -> {
                val selectedOption = event.selectedOption
                val isBlueSection = event.isBlueSection
                viewModelScope.launch {
                    if (!mutex.tryLock()) return@launch

                    try {
                        val correctAnswer =
                            _gameState.value.options?.find { it.answer }?.option ?: return@launch
                        val isCorrect = selectedOption == correctAnswer

                        var newBlueScore = _gameState.value.blueScore
                        var newRedScore = _gameState.value.redScore

                        if (isBlueSection) {
                            if (isCorrect) newBlueScore++ else newRedScore++
                            _gameState.value = _gameState.value.copy(selectedBlueOption = selectedOption)
                        } else {
                            if (isCorrect) newRedScore++ else newBlueScore++
                            _gameState.value = _gameState.value.copy(selectedRedOption = selectedOption)
                        }

                        _gameState.value = _gameState.value.copy(
                            blueScore = newBlueScore,
                            redScore = newRedScore
                        )

                        delay(3000)
                        nextQuestion()

                    } finally {
                        mutex.unlock()
                    }
                }
            }

            is GameEvent.SetMaxWinningPoints -> {
                _gameState.value = _gameState.value.copy(
                    maxWinningPoints = event.maxWinningPoints
                )
            }

            is GameEvent.OnExitClicked -> {
                event.navigator.popBack()
                analyticsLogger.log(LogEvents.EXIT_GAME)
            }

            GameEvent.OnReset -> {
                _gameState.value = GameState()
            }

            GameEvent.StartCountDownAndNextQuestion -> {
                viewModelScope.launch {
                    _gameState.value = _gameState.value.copy(countdown = 3)
                    delay(1000)
                    _gameState.value = _gameState.value.copy(countdown = 2)
                    delay(1000)
                    _gameState.value = _gameState.value.copy(countdown = 1)
                    delay(1000)
                    _gameState.value = _gameState.value.copy(countdown = null)
                    nextQuestion()
                }
            }

            is GameEvent.InitializeGameModeAndBotLevel -> {
                _gameState.value = _gameState.value.copy(
                    gameMode = event.gameMode,
                    botLevel = event.botLevel
                )
            }
        }
    }

    private fun nextQuestion() {
        val operands = generateOperands()
        val operation = generateOperation()
        val answer = calculateAnswer(operands, operation)
        val options = calculateOptions(answer)

        _gameState.value = _gameState.value.copy(
            operands = operands,
            operation = operation,
            options = options,
            selectedRedOption = null,
            selectedBlueOption = null
        )

    }

    private fun calculateOptions(answer: Answer): List<Option> {
        val random = Random()
        val correctAnswer = answer.answer

        val options = mutableListOf<Option>()
        options.add(Option(correctAnswer, true))

        val placementPattern = if (correctAnswer <= 3) 0 else random.nextInt(3)
        val incorrectOptions = mutableListOf<Int>()

        var attempts = 0
        val maxAttempts = 20

        while (incorrectOptions.size < 2 && attempts < maxAttempts) {
            val randomOffset = random.nextInt(16) + 2
            val incorrectValue = when (placementPattern) {
                0 -> correctAnswer + randomOffset
                1 -> if (incorrectOptions.isEmpty()) correctAnswer - randomOffset else correctAnswer + randomOffset
                2 -> correctAnswer - randomOffset
                else -> correctAnswer + randomOffset
            }

            if (incorrectValue > 0 && incorrectValue != correctAnswer && !incorrectOptions.contains(
                    incorrectValue
                )
            ) {
                incorrectOptions.add(incorrectValue)
            }

            attempts++
        }

        while (incorrectOptions.size < 2) {
            val offset = when (incorrectOptions.size) {
                0 -> correctAnswer + random.nextInt(20)
                else -> correctAnswer + random.nextInt(7)
            }

            val value = correctAnswer + offset

            if (value > 0 && value != correctAnswer && !incorrectOptions.contains(value)) {
                incorrectOptions.add(value)
            }
        }

        options.add(Option(incorrectOptions[0], false))
        options.add(Option(incorrectOptions[1], false))

        return options.shuffled()
    }


    private fun calculateAnswer(operands: List<Operand>, operation: Int): Answer {
        val first = operands[0].operand
        val second = operands[1].operand

        return when (operation) {
            1 -> Answer(first + second)
            2 -> Answer(first - second)
            3 -> Answer(first * second)
            4 -> Answer(first / second)
            else -> throw IllegalArgumentException("Invalid operation")
        }
    }

    private fun generateOperands(): List<Operand> {
        val random = Random()
        val randomOperator1 = random.nextInt(15) + 1
        val randomOperator2 = random.nextInt(15) + 2

        return listOf(
            Operand(randomOperator1 * randomOperator2),
            Operand(randomOperator1)
        )
    }

    private fun generateOperation(): Int {
        val random = Random()
        return random.nextInt(4) + 1
    }
}