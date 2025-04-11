package eu.tutorials.mathgame.data.state

import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.data.model.Option
import eu.tutorials.mathgame.ui.viewmodel.ButtonSituation

data class GameState(
    val operands: List<Operand>? = emptyList(),
    val operation: Int = 0,
    val options: List<Option>? = emptyList(),
    val blueScore: Int = 0,
    val redScore: Int = 0,
    val selectedBlueOption: Int? = null,
    val selectedRedOption: Int? = null,
    val isExitClicked: Boolean = false,
    val countdown: Int? = null,
    val buttonSituation: ButtonSituation? = null
)
