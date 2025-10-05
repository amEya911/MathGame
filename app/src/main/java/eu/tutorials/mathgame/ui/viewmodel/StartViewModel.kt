package eu.tutorials.mathgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.AppScreen
import eu.tutorials.mathgame.navigation.replaceWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StartViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    private val _startState = MutableStateFlow(StartState())
    val startState: StateFlow<StartState> = _startState

    val config get() = remoteConfig

    fun onEvent(event: StartEvent) {
        when (event) {
            is StartEvent.OnNormalModeClicked -> {
                _startState.value = StartState(
                    gameMode = GameMode.NORMAL,
                    isGameStartTriggered = true
                )
                onEvent(StartEvent.NavigateToGameScreen(event.navigator))
            }

            StartEvent.OnBotModeClicked -> {
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.BOT
                )
            }

            is StartEvent.OnBotLevelSelected -> {
                _startState.value = _startState.value.copy(
                    botLevel = event.level,
                    isGameStartTriggered = true
                )
                onEvent(StartEvent.NavigateToGameScreen(event.navigator))
            }

            StartEvent.OnReset -> {
                _startState.value = StartState()
            }

            StartEvent.OnBackClicked -> {
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.NORMAL
                )
            }

            is StartEvent.NavigateToGameScreen -> {
                val gameMode = _startState.value.gameMode
                val botLevel = _startState.value.botLevel
                event.navigator.replaceWith("${AppScreen.Game.route}/$gameMode?botLevel=$botLevel")
                onEvent(StartEvent.OnReset)
            }

            is StartEvent.ChangeSliderPosition -> {
                val oldLevel = _startState.value.previousLevel
                val levels = listOf(BotLevel.EASY, BotLevel.MEDIUM, BotLevel.HARD)
                val newLevel = levels[event.newPosition.roundToInt()]

                _startState.value = _startState.value.copy(
                    sliderPosition = event.newPosition,
                    isLevelIncreasing = newLevel.ordinal > oldLevel.ordinal,
                    previousLevel = newLevel
                )
            }
        }
    }
}
