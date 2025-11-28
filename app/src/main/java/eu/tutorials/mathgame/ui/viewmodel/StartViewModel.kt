package eu.tutorials.mathgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.datasource.local.RoundsDataStoreManager
import eu.tutorials.mathgame.data.datasource.remote.AnalyticsLogger
import eu.tutorials.mathgame.data.datasource.remote.LogEvents
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.AppScreen
import eu.tutorials.mathgame.navigation.replaceWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StartViewModel @Inject constructor(
    private val roundsDataStore: RoundsDataStoreManager,
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    private val _startState = MutableStateFlow(StartState())
    val startState: StateFlow<StartState> = _startState

    init {
        analyticsLogger.log(LogEvents.START_SCREEN_OPENED)
        viewModelScope.launch {
            val savedLevel = roundsDataStore.levelSliderPositionFlow.first()
            _startState.value = _startState.value.copy(levelSliderPosition = savedLevel)
        }
    }

    fun onEvent(event: StartEvent) {
        when (event) {
            is StartEvent.OnNormalModeClicked -> {
                analyticsLogger.log(LogEvents.FRIEND_MODE_SELECTED)
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.NORMAL,
                    isGameStartTriggered = true
                )
                onEvent(StartEvent.NavigateToGameScreen(event.navigator))
            }

            StartEvent.OnBotModeClicked -> {
                analyticsLogger.log(LogEvents.BOT_MODE_SELECTED)
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.BOT
                )
            }

            is StartEvent.OnBotLevelSelected -> {
                analyticsLogger.log(LogEvents.BOT_LEVEL_SELECTED) {
                    putString("bot_level", event.level.name)
                }
                _startState.value = _startState.value.copy(
                    botLevel = event.level,
                    isGameStartTriggered = true
                )
                onEvent(StartEvent.NavigateToGameScreen(event.navigator))
            }

            StartEvent.OnReset -> {
                _startState.value = StartState(
                    levelSliderPosition = _startState.value.levelSliderPosition
                )
            }

            StartEvent.OnBackClicked -> {
                analyticsLogger.log(LogEvents.BACK_BUTTON_PRESSED)
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.NORMAL
                )
            }

            is StartEvent.NavigateToGameScreen -> {
                val gameMode = _startState.value.gameMode
                val botLevel = _startState.value.botLevel
                val maxWinningPoints = _startState.value.levelSliderPosition.inc().toLong()
                viewModelScope.launch {
                    roundsDataStore.saveLevelSliderPosition(_startState.value.levelSliderPosition)
                }
                event.navigator.replaceWith("${AppScreen.Game.route}/$gameMode/$maxWinningPoints?botLevel=$botLevel")
                onEvent(StartEvent.OnReset)
                analyticsLogger.log(LogEvents.GAME_STARTED) {
                    putString("game_mode", _startState.value.gameMode.name)
                    putString("bot_level", _startState.value.botLevel?.name)
                    putLong("rounds", maxWinningPoints)
                }
                analyticsLogger.log(LogEvents.NUMBER_OF_ROUNDS_SELECTED) {
                    putLong("rounds", _startState.value.levelSliderPosition.inc().toLong())
                }
            }

            is StartEvent.ChangeSliderPosition -> {
                val oldLevel = _startState.value.previousLevel
                val levels = listOf(BotLevel.EASY, BotLevel.MEDIUM, BotLevel.HARD)
                val newLevel = levels[event.newPosition.roundToInt()]

                _startState.value = _startState.value.copy(
                    sliderPosition = event.newPosition,
                    isBotLevelIncreasing = newLevel.ordinal > oldLevel.ordinal,
                    previousLevel = newLevel
                )
            }

            is StartEvent.ChangeLevelSliderPosition -> {
                val oldRound = _startState.value.previousLevelSliderPosition
                val newRound = event.newPosition

                val isRoundLevelIncreasing = newRound > oldRound

                _startState.value = _startState.value.copy(
                    levelSliderPosition = event.newPosition,
                    previousLevelSliderPosition = newRound,
                    isRoundLevelIncreasing = isRoundLevelIncreasing
                )
            }

        }
    }
}
