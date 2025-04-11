package eu.tutorials.mathgame.data.event

import eu.tutorials.mathgame.ui.viewmodel.ButtonSituation

sealed class GameEvent {
    data object OnNextQuestion: GameEvent()
    data class OnOptionButtonClicked(val selectedOption: Int, val isBlueSection: Boolean): GameEvent()
    data object OnExitClicked: GameEvent()
    data object OnReset: GameEvent()
}