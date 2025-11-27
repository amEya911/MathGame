package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.tutorials.mathgame.data.model.Option
import eu.tutorials.mathgame.ui.theme.AppTheme

@Composable
fun Options(
    options: List<Option>,
    color: Color,
    borderColor: Color,
    selectedOption: Int?,
    enabled: Boolean,
    onOptionClick: (Int) -> Unit,
    onOptionPositioned: ((Rect) -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isCorrect = option.answer

            val backgroundColor = when {
                selectedOption == null -> color
                option.option == selectedOption && isCorrect -> AppTheme.colors.correctAnswerColor
                option.option == selectedOption && !isCorrect -> AppTheme.colors.wrongAnswerColor
                else -> color
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .onGloballyPositioned { layoutCoordinates ->
                        val bounds = layoutCoordinates.boundsInRoot()
                        if (option.option == selectedOption) {
                            onOptionPositioned?.invoke(bounds)
                        }
                    }
            ) {
                Button(
                    onClick = {
                        if (selectedOption == null) onOptionClick(option.option)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(4.dp, borderColor, RoundedCornerShape(100))
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundColor,
                        disabledContainerColor = backgroundColor
                    ),
                    enabled = enabled
                ) {
                    Text(
                        text = option.option.toString(),
                        color = borderColor,
                        style = AppTheme.typography.xLarge,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
