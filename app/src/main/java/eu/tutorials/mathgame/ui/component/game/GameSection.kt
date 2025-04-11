package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.model.Operand
import eu.tutorials.mathgame.data.model.Option

@Composable
fun GameSection(
    operands: List<Operand>,
    operation: Int,
    options: List<Option>,
    rotation: Float,
    color: Color,
    borderColor: Color,
    selectedOption: Int?,
    enabled: Boolean,
    onOptionClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.rotate(rotation)
    ) {
        Question(operands, operation)
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(
            modifier = Modifier.clip(RoundedCornerShape(100)),
            thickness = 10.dp,
            color = borderColor
        )
        Spacer(modifier = Modifier.height(32.dp))
        Options(options, color, borderColor, selectedOption, enabled, onOptionClick)
    }
}
