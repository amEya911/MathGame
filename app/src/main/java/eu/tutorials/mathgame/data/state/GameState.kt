package eu.tutorials.mathgame.data.state

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.data.model.Option

data class GameState(
    val maxWinningPoints: Long? = null,
    val selectedButtonRect: Rect? = null,
    val circleRadius: Dp = 0.dp,
    val gameMode: GameMode = GameMode.NORMAL,
    val botLevel: BotLevel? = null,
    val operands: List<Operand>? = emptyList(),
    val operation: Int = 0,
    val options: List<Option>? = emptyList(),
    val blueScore: Int = 0,
    val redScore: Int = 0,
    val selectedBlueOption: Int? = null,
    val selectedRedOption: Int? = null,
    val isExitClicked: Boolean = false,
    val countdown: Int? = null
)
