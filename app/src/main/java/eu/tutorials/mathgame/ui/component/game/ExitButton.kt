package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel

@Composable
fun ExitButton(gameViewModel: GameViewModel, navigator: Navigator) {
    Button(
        onClick = { gameViewModel.onEvent(GameEvent.OnExitClicked(navigator)) },
        modifier = Modifier
            .height(80.dp)
            .width(80.dp),
        shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.textWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "EXIT",
                color = AppTheme.colors.textBlack,
                modifier = Modifier.padding(bottom = 25.dp, end = 20.dp),
                style = AppTheme.typography.large.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
        }
    }
}