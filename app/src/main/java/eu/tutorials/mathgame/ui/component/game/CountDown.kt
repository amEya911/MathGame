package eu.tutorials.mathgame.ui.component.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import eu.tutorials.mathgame.ui.theme.AppTheme

@Composable
fun CountDown(countdown: Int) {
    val scale = remember { Animatable(0.5f) }
    LaunchedEffect(countdown) {
        scale.snapTo(0.5f)
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.textBlack.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = countdown.toString(),
            style = AppTheme.typography.xxxxxLarge,
            color = AppTheme.colors.textWhite,
            modifier = Modifier.scale(scale.value)
        )
    }
}