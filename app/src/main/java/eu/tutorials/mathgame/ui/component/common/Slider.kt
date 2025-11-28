package eu.tutorials.mathgame.ui.component.common

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    .background(color, shape = CircleShape)
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