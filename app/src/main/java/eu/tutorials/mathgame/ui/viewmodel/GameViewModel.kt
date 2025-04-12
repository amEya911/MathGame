package eu.tutorials.mathgame.ui.viewmodel

import android.util.Log
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.Answer
import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.data.model.Option
import eu.tutorials.mathgame.data.state.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private val mutex = Mutex()

    init {
        startCountdownAndNextQuestion()
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.OnNextQuestion -> {
                nextQuestion()
            }

            is GameEvent.OnOptionButtonClicked -> {
                handleOptionClick(event.selectedOption, event.isBlueSection)
            }

            GameEvent.OnExitClicked -> {
                _gameState.value = _gameState.value.copy(
                    isExitClicked = true
                )
            }

            GameEvent.OnReset -> {
                _gameState.value = GameState()
            }
        }
    }

    private fun startCountdownAndNextQuestion() {
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


    private fun handleOptionClick(selectedOption: Int, isBlueSection: Boolean) {
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
                withContext(Dispatchers.Default) {
                    nextQuestion()
                }

            } finally {
                mutex.unlock()
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

        val placementPattern = random.nextInt(3)

        val incorrectOptions = mutableListOf<Int>()
        while (incorrectOptions.size < 2) {
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