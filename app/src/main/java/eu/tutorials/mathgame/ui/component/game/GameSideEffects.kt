package eu.tutorials.mathgame.ui.component.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import eu.tutorials.mathgame.util.AppLifecycle.rememberAppInForeground
import eu.tutorials.mathgame.util.FirebaseUtils
import eu.tutorials.mathgame.util.Sound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameSideEffects(
    gameViewModel: GameViewModel,
    gameState: GameState,
) {
    val remoteConfig = gameViewModel.config
    val isAppInForeground = rememberAppInForeground()
    val countdown = gameState.countdown
    val context = LocalContext.current
    val isSelected = gameState.selectedBlueOption != null || gameState.selectedRedOption != null
    val maxWinningPoints = gameState.maxWinningPoints

    val someoneWon = if (maxWinningPoints != null && maxWinningPoints != 0L) {
        gameState.blueScore == maxWinningPoints.toInt() || gameState.redScore == maxWinningPoints.toInt()
    } else {
        false
    }

    LaunchedEffect(isSelected) {
        if (isSelected) {
            gameViewModel.onEvent(GameEvent.ChangeCircleRadius(350.dp))
            delay(2500)
            gameViewModel.onEvent(GameEvent.ChangeCircleRadius(0.dp))
        }
    }

    LaunchedEffect(countdown, isAppInForeground) {
        if (!isAppInForeground) return@LaunchedEffect

        val soundRes = when (countdown) {
            1, 2, 3 -> R.raw.beep
            null -> R.raw.start
            else -> null
        }

        soundRes?.let { Sound.playSound(context, it) }
    }


    if (gameState.gameMode == GameMode.BOT && isAppInForeground && !someoneWon) {
        LaunchedEffect(
            gameState.operands,
            gameState.selectedBlueOption,
            gameState.selectedRedOption
        ) {
            if (
                gameState.operands != null &&
                gameState.selectedBlueOption == null &&
                gameState.selectedRedOption == null
            ) {
                val botConfig = FirebaseUtils.getBotConfig(remoteConfig, gameState.botLevel)
                val (minDelay, maxDelay, accuracy) = botConfig

                delay(Random.nextLong(minDelay, maxDelay))
                val options = gameState.options.orEmpty()

                val botChoice = if (Random.nextFloat() < accuracy)
                    options.firstOrNull { it.answer }
                else
                    options.filterNot { it.answer }.randomOrNull()

                botChoice?.let {
                    gameViewModel.onEvent(GameEvent.OnOptionButtonClicked(it.option, true))
                }
            }
        }
    }
}