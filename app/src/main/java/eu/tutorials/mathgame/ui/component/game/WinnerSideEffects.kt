package eu.tutorials.mathgame.ui.component.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.Winner
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import eu.tutorials.mathgame.util.FirebaseUtils
import kotlinx.coroutines.delay

@Composable
fun WinnerSideEffects(
    winner: Winner?,
    gameViewModel: GameViewModel,
    navigator: Navigator
) {
    if (winner == null) return

    val remoteConfig = gameViewModel.config
    val winnerDisplayDuration = FirebaseUtils.getWinnerDisplayDuration(remoteConfig).time


    LaunchedEffect(winner) {
        delay(3000)
        gameViewModel.onEvent(GameEvent.ShowWinnerBox)
        delay(winnerDisplayDuration)
        gameViewModel.onEvent(GameEvent.NavigateBackStack(navigator))
    }
}