package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.theme.AppTheme

@Composable
fun ScoreIndicator(
    gameState: GameState
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .rotate(-90f)
            .padding(top = 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = gameState.redScore.toString(),
            color = AppTheme.colors.playerOnePrimary,
            style = AppTheme.typography.medium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = gameState.blueScore.toString(),
            color = AppTheme.colors.playerTwoPrimary,
            style = AppTheme.typography.medium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}