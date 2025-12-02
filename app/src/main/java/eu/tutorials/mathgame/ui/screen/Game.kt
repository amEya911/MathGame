package eu.tutorials.mathgame.ui.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.component.game.AnimationExplosion
import eu.tutorials.mathgame.ui.component.game.CountDown
import eu.tutorials.mathgame.ui.component.game.ExitButton
import eu.tutorials.mathgame.ui.component.game.GameSideEffects
import eu.tutorials.mathgame.ui.component.game.PlayerSections
import eu.tutorials.mathgame.ui.component.game.ScoreIndicator
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun Game(
    gameViewModel: GameViewModel,
    gameState: GameState,
    navigator: Navigator
) {
    val maxWinningPoints = gameState.maxWinningPoints
    val countdown = gameState.countdown

    val isSelected = gameState.selectedBlueOption != null || gameState.selectedRedOption != null
    val circleRadius = gameState.circleRadius

    val selectedButtonRect = gameState.selectedButtonRect

    val animatedRadius by animateDpAsState(
        targetValue = circleRadius,
        animationSpec = tween(durationMillis = 500),
        label = "circleAnimation"
    )

    Log.d("Ameya", "Game Options: ${gameState.options}")

    BackHandler {
        gameViewModel.onEvent(GameEvent.OnExitClicked(navigator))
    }

    GameSideEffects(
        gameViewModel = gameViewModel,
        gameState = gameState,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.botModeBackground)
        ) {
            if (isSelected && selectedButtonRect != null) {
                AnimationExplosion(gameState, selectedButtonRect, animatedRadius)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(100.dp)
                    .offset(x = (-50).dp)
                    .clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100))
                    .background(AppTheme.colors.backGroundWhite)
            ) {
                ScoreIndicator(gameState)
            }

            PlayerSections(
                gameState = gameState,
                gameViewModel = gameViewModel
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 40.dp)
                    .rotate(-90f)
            ) {
                ExitButton(gameViewModel, navigator)
            }
        }

        if (countdown != null) {
            CountDown(countdown)
        }
    }

    val winner = getWinner(maxWinningPoints, gameState)

    WinnerSideEffects(winner, gameViewModel, navigator)

    WinnerOverlay(winner, gameState)
}

fun getWinner(maxWinningPoints: Long?, gameState: GameState): Winner? {
    if (maxWinningPoints == null || maxWinningPoints == 0L) return null

    val target = maxWinningPoints.toInt()

    return when {
        gameState.blueScore == target -> Winner.BLUE
        gameState.redScore == target -> Winner.RED
        else -> null
    }
}

enum class Winner { BLUE, RED }

@Composable
fun WinnerSideEffects(
    winner: Winner?,
    gameViewModel: GameViewModel,
    navigator: Navigator
) {
    if (winner == null) return

    LaunchedEffect(winner) {
        delay(3000)
        gameViewModel.onEvent(GameEvent.ShowWinnerBox)
        delay(2000)
        gameViewModel.onEvent(GameEvent.NavigateBackStack(navigator))
    }
}

@Composable
fun WinnerOverlay(
    winner: Winner?,
    gameState: GameState
) {
    if (winner == null || !gameState.showWinnerBox) return

    val backgroundColor = when (winner) {
        Winner.BLUE -> AppTheme.colors.primaryInverseColor
        Winner.RED -> AppTheme.colors.primaryColor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (winner == Winner.BLUE) "Blue Wins!" else "Red Wins!",
            style = AppTheme.typography.xLarge.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.textWhite
        )
    }
}
