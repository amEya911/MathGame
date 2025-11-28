package eu.tutorials.mathgame.ui.component.game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.util.OperationSymbol

@Composable
fun Question(operands: List<Operand>, operation: Int) {
    if (operands.size < 2) return
    Text(
        text = "${operands[0].operand} ${OperationSymbol.getOperationSymbol(operation)} ${operands[1].operand}",
        color = AppTheme.colors.textBlack,
        style = AppTheme.typography.xxxLarge
    )
}
