package eu.tutorials.mathgame.data.event

import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.navigation.Navigator

sealed interface GameEvent {
    data object OnNextQuestion: GameEvent
    data class NavigateBackStack(val navigator: Navigator): GameEvent
    data class InitializeGameModeAndBotLevel(val gameMode: GameMode, val botLevel: BotLevel?): GameEvent
    data object StartCountDownAndNextQuestion: GameEvent
    data class OnOptionButtonClicked(val selectedOption: Int, val isBlueSection: Boolean): GameEvent
    data object OnExitClicked: GameEvent
    data object OnReset: GameEvent
}