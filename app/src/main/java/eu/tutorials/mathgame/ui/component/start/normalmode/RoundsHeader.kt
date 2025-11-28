package eu.tutorials.mathgame.ui.component.start.normalmode

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val isIncreasing = startState.isRoundLevelIncreasing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // "First to" + "points wins" as static/marquee text
        Text(
            text = "First to",
            style = AppTheme.typography.large.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.textBlack,
            maxLines = 1,
            modifier = Modifier
                .basicMarquee()
        )

        AnimatedContent(
            targetState = rounds,
            transitionSpec = {
                if (isIncreasing) {
                    (slideInVertically(
                        animationSpec = tween(250)
                    ) { height -> height } + fadeIn()) togetherWith
                            (slideOutVertically(
                                animationSpec = tween(250)
                            ) { height -> -height } + fadeOut())
                } else {
                    (slideInVertically(
                        animationSpec = tween(250)
                    ) { height -> -height } + fadeIn()) togetherWith
                            (slideOutVertically(
                                animationSpec = tween(250)
                            ) { height -> height } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "rounds_animation"
        ) { value ->
            Text(
                text = value.toString(),
                style = AppTheme.typography.large.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = AppTheme.colors.textBlack,
                modifier = Modifier.graphicsLayer {
                    cameraDistance = 8 * density
                    rotationY = if (isIncreasing) -10f else 10f
                }
            )
        }

        Text(
            text = "points wins",
            style = AppTheme.typography.large.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppTheme.colors.textBlack,
            maxLines = 1,
        )
    }
}
