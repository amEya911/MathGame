package eu.tutorials.mathgame.ui.component.start.botmode

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import eu.tutorials.mathgame.data.state.StartState

@Composable
fun BotImage(painterId: Int, color: Color, startState: StartState) {
    val isLevelIncreasing = startState.isLevelIncreasing
    Box(
        modifier = Modifier
            .size(100.dp)
            .offset(y = 20.dp)
            .zIndex(1f)
            .background(color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = painterId,
            transitionSpec = {
                val direction = if (isLevelIncreasing) 1 else -1
                slideInVertically(tween(300)) { it * direction } togetherWith
                        slideOutVertically(tween(300)) { -it * direction }
            },
            label = "BotLevelImageAnimation"
        ) { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = "Bot Level Icon",
                modifier = Modifier.size(80.dp),
            )
        }
    }
}