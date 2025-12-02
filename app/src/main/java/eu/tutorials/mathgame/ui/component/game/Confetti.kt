package eu.tutorials.mathgame.ui.component.game

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.ui.theme.AppTheme
import kotlin.random.Random

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Confetti(
    piecesCount: Int = 100
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val infiniteTransition = rememberInfiniteTransition(label = "confetti_transition")

    val confettiPalette = listOf(
        AppTheme.colors.confettiColors.purple,
        AppTheme.colors.confettiColors.blue,
        AppTheme.colors.confettiColors.green,
        AppTheme.colors.confettiColors.yellow,
        AppTheme.colors.confettiColors.red
    )

    val pieces = remember {
        List(piecesCount) {
            ConfettiPiece(
                startX = Random.nextFloat(),
                width = (4..10).random().toFloat(),
                height = (8..18).random().toFloat(),
                durationMillis = (1500..3500).random(),
                delayMillis = (0..1200).random(),
                color = confettiPalette.random()
            )
        }
    }

    val animatedFractions = pieces.mapIndexed { index, piece ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = piece.durationMillis,
                    delayMillis = piece.delayMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "confetti_$index"
        )
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val widthPx = screenWidth.toPx()
        val heightPx = screenHeight.toPx()

        val spawnOffsetPx = 40.dp.toPx()

        pieces.forEachIndexed { index, piece ->
            val fraction = animatedFractions[index].value

            val x = piece.startX * widthPx
            val y = -spawnOffsetPx + fraction * (heightPx + spawnOffsetPx * 2)

            drawRect(
                color = piece.color,
                topLeft = Offset(x, y),
                size = Size(piece.width, piece.height)
            )
        }
    }
}

data class ConfettiPiece(
    val startX: Float,
    val width: Float,
    val height: Float,
    val durationMillis: Int,
    val delayMillis: Int,
    val color: Color
)