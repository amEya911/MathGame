package eu.tutorials.mathgame.ui.component.start.botmode

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.ui.component.common.Slider
import eu.tutorials.mathgame.ui.theme.AppTheme

@Composable
fun BotLevelCard(
    selectedLevel: BotLevel,
    color: Color,
    sliderPosition: Float,
    onSliderChange: (Float) -> Unit,
    onPlayClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(500.dp)
            .width(350.dp)
            .background(AppTheme.colors.botModeBox, shape = MaterialTheme.shapes.medium)
            .zIndex(-1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = selectedLevel,
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1
                    slideInVertically(tween(300)) { it * direction } togetherWith
                            slideOutVertically(tween(300)) { -it * direction }
                },
                label = "BotLevelTextAnimation"
            ) { level ->
                Text(
                    text = level.name,
                    style = AppTheme.typography.xxxLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = color
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                sliderPosition = sliderPosition,
                onValueChange = onSliderChange,
                color = color
            )

            Text(
                text = "Drag to adjust\ndifficulty",
                color = Color.Black,
                style = AppTheme.typography.xLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            PlayButton(color = color, onClick = onPlayClicked)
        }
    }
}