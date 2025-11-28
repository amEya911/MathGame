package eu.tutorials.mathgame.ui.component.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Slider(
    sliderPosition: Float,
    onValueChange: (Float) -> Unit,
    sliderHeight: Dp = 60.dp,
    thumbHeight: Dp = 48.dp,
    trackHeight: Dp = 36.dp,
    padding: Dp = 32.dp,
    endValue: Float = 2f,
    color: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scaleAnim by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(950, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Slider(
        value = sliderPosition,
        onValueChange = onValueChange,
        valueRange = 0f..endValue,
        modifier = Modifier
            .padding(horizontal = padding)
            .fillMaxWidth()
            .height(sliderHeight),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = Color.LightGray,
            inactiveTrackColor = Color.Gray,
            disabledThumbColor = color,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        ),

        thumb = {
            Box(
                modifier = Modifier
                    .size(thumbHeight)
                    .graphicsLayer {
                        scaleX = scaleAnim
                        scaleY = scaleAnim
                    }
                    .background(color, CircleShape)
                    .border(12.dp, Color.White, CircleShape)
            )
        },

        track = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .background(Color.LightGray, RoundedCornerShape(50))
                    .border(8.dp, Color.White, RoundedCornerShape(50))
            )
        }
    )
}