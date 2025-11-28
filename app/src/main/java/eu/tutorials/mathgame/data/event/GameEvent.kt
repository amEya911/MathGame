package eu.tutorials.mathgame.data.event

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.navigation.Navigator

sealed interface GameEvent {
    data object OnNextQuestion: GameEvent
    data class ChangeCircleRadius(val newRadius: Dp): GameEvent
    data class ChangeSelectedButtonRect(val newRect: Rect?): GameEvent
    data object ShowWinnerBox: GameEvent
    data class NavigateBackStack(val navigator: Navigator): GameEvent
    data class InitializeGameModeAndBotLevel(val gameMode: GameMode, val botLevel: BotLevel?): GameEvent
    data object StartCountDownAndNextQuestion: GameEvent
    data class SetMaxWinningPoints(val maxWinningPoints: Long): GameEvent
    data class OnOptionButtonClicked(val selectedOption: Int, val isBlueSection: Boolean): GameEvent
    data class OnExitClicked(val navigator: Navigator): GameEvent
    data object OnReset: GameEvent
}