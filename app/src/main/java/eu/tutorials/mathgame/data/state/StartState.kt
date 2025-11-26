package eu.tutorials.mathgame.data.state

import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode

data class StartState(
    val sliderPosition: Float = 0f,
    val levelSliderPosition: Float = 0f,
    val previousLevel: BotLevel = BotLevel.EASY,
    val isLevelIncreasing: Boolean = true,
    val gameMode: GameMode = GameMode.NORMAL,
    val botLevel: BotLevel? = null,
    val isGameStartTriggered: Boolean = false
)