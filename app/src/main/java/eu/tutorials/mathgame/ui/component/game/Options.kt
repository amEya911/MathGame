package eu.tutorials.mathgame.ui.component.game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.model.Option
import eu.tutorials.mathgame.util.Sound
import kotlinx.coroutines.delay

@Composable
fun Options(
    options: List<Option>,
    color: Color,
    borderColor: Color,
    selectedOption: Int?,
    enabled: Boolean,
    onOptionClick: (Int) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option.option
            val isCorrect = option.answer

            var circleRadius by remember { mutableStateOf(0.dp) }

            val animatedRadius by animateDpAsState(
                targetValue = circleRadius,
                animationSpec = tween(durationMillis = 500),
                label = "circleAnimation"
            )

            LaunchedEffect(selectedOption) {
                if (isSelected) {
                    Sound.playSound(context, if (isCorrect) R.raw.win else R.raw.lose)
                    circleRadius = 350.dp
                    delay(2500)
                    circleRadius = 0.dp
                }
            }

            val backgroundColor = when {
                selectedOption == null -> color
                option.option == selectedOption && isCorrect -> MaterialTheme.colorScheme.errorContainer
                option.option == selectedOption && !isCorrect -> MaterialTheme.colorScheme.error
                else -> color
            }

            Box(
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(100.dp)
                ) {
                    if (isSelected) {
                        drawCircle(
                            color = backgroundColor,
                            radius = animatedRadius.toPx(),
                            alpha = 0.5f
                        )
                    }
                }

                Button(
                    onClick = {
                        if (selectedOption == null) onOptionClick(option.option)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .border(4.dp, borderColor, RoundedCornerShape(100))
                        .size(width = 110.dp, height = 70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundColor,
                        disabledContainerColor = backgroundColor
                    ),
                    enabled = enabled
                ) {
                    Text(
                        text = option.option.toString(),
                        color = borderColor,
                        fontSize = 24.sp,
                    )
                }
            }
        }
    }
}
