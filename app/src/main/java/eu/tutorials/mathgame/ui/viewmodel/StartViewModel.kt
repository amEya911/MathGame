package eu.tutorials.mathgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.StartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {

    private val _startState = MutableStateFlow(StartState())
    val startState: StateFlow<StartState> = _startState

    fun onEvent(event: StartEvent) {
        when (event) {
            StartEvent.OnNormalModeClicked -> {
                _startState.value = StartState(
                    gameMode = GameMode.NORMAL,
                    isGameStartTriggered = true
                )
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
            }

            StartEvent.OnReset -> {
                _startState.value = StartState()
            }

            StartEvent.OnBackClicked -> {
                _startState.value = _startState.value.copy(
                    gameMode = GameMode.NORMAL
                )
            }
        }
    }
}
