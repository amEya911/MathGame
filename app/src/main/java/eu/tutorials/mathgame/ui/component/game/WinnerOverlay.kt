package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import eu.tutorials.mathgame.data.model.Winner
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.theme.AppTheme

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
        Confetti()

        Text(
            text = if (winner == Winner.BLUE) "Blue Wins!" else "Red Wins!",
            style = AppTheme.typography.xLarge.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.textWhite
        )
    }
}