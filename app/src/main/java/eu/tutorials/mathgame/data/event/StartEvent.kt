package eu.tutorials.mathgame.data.event

import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.navigation.Navigator

sealed interface StartEvent {
    data class OnNormalModeClicked(val navigator: Navigator) : StartEvent
    data class ChangeSliderPosition(val newPosition: Float) : StartEvent
    data object OnBotModeClicked : StartEvent
    data class OnBotLevelSelected(val level: BotLevel, val navigator: Navigator) : StartEvent
    data class NavigateToGameScreen(val navigator: Navigator): StartEvent
    data object OnReset : StartEvent
    data object OnBackClicked: StartEvent
}
