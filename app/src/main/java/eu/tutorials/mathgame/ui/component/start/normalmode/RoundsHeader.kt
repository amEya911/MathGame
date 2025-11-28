package eu.tutorials.mathgame.ui.component.start.normalmode

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.ui.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RoundsHeader(startState: StartState) {
    val rounds = startState.levelSliderPosition.toInt().inc()

    // Track previous value to know direction (increase/decrease)
    var previousRounds by remember { mutableStateOf(rounds) }
    val isIncreasing = rounds > previousRounds

    LaunchedEffect(rounds) {
        previousRounds = rounds
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Select number of rounds",
            style = AppTheme.typography.large.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.textBlack
        )

        AnimatedContent(
            targetState = rounds,
            transitionSpec = {
                if (isIncreasing) {
                    // Number increased → old goes left, new comes from right
                    (slideInHorizontally(
                        animationSpec = tween(250)
                    ) { fullWidth -> fullWidth } + fadeIn()) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { fullWidth -> -fullWidth } + fadeOut())
                } else {
                    // Number decreased → old goes right, new comes from left
                    (slideInHorizontally(
                        animationSpec = tween(250)
                    ) { fullWidth -> -fullWidth } + fadeIn()) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { fullWidth -> fullWidth } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "rounds_animation"
        ) { value ->
            Text(
                text = value.toString(),
                style = AppTheme.typography.large.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppTheme.colors.textBlack,
                modifier = Modifier.graphicsLayer {
                    cameraDistance = 8 * density
                    rotationY = if (isIncreasing) -10f else 10f
                }
            )
        }
    }
}