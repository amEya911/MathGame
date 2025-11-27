package eu.tutorials.mathgame.ui.component.game

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.util.Sound

@Composable
fun AnimationExplosion(
    gameState: GameState,
    selectedButtonRect: Rect?,
    animatedRadius: Dp
) {
    val selectedOptionText = gameState.selectedBlueOption ?: gameState.selectedRedOption
    val selectedOption = gameState.options?.firstOrNull { it.option == selectedOptionText }
    val isCorrect = selectedOption?.answer == true
    val context = LocalContext.current

    LaunchedEffect(selectedOption) {
        Sound.playSound(context, if (isCorrect) R.raw.win else R.raw.lose)
    }

    val backgroundColor = if (isCorrect)
        AppTheme.colors.correctAnswerColor
    else
        AppTheme.colors.wrongAnswerColor

    Box(
        modifier = Modifier
            .absoluteOffset {
                val rect = selectedButtonRect!!
                val baseX = rect.center.x - animatedRadius.toPx() / 2
                val baseY = rect.center.y - animatedRadius.toPx() / 2
                IntOffset(baseX.toInt(), baseY.toInt())
            }
            .size(animatedRadius)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = backgroundColor,
                radius = size.minDimension
            )
        }
    }
}