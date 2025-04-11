package eu.tutorials.mathgame.data.event

import eu.tutorials.mathgame.data.model.BotLevel

sealed class StartEvent {
    data object OnNormalModeClicked : StartEvent()
    data object OnBotModeClicked : StartEvent()
    data class OnBotLevelSelected(val level: BotLevel) : StartEvent()
    data object OnReset : StartEvent()
    data object OnBackClicked: StartEvent()
}
