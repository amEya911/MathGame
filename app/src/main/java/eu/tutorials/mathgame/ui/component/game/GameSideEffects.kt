package eu.tutorials.mathgame.ui.component.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.util.FirebaseUtils
import eu.tutorials.mathgame.util.Sound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameSideEffects(
    context: android.content.Context,
    gameState: GameState,
    gameMode: GameMode,
    botLevel: BotLevel?,
    isSelected: Boolean,
    onExitClicked: () -> Unit,
    updateCircleRadius: (Dp) -> Unit,
    onBotAnswer: (Int, Boolean) -> Unit,
    onReset: () -> Unit,
    isAppInForeground: Boolean,
    maxWinningPoints: Long?
) {
    val countdown = gameState.countdown

    val someoneWon = if (maxWinningPoints != null && maxWinningPoints != 0L) {
        gameState.blueScore == maxWinningPoints.toInt() || gameState.redScore == maxWinningPoints.toInt()
    } else {
        false
    }

    LaunchedEffect(isSelected) {
        if (isSelected) {
            updateCircleRadius(350.dp)
            delay(2500)
            updateCircleRadius(0.dp)
        }
    }

    LaunchedEffect(gameState.isExitClicked) {
        if (gameState.isExitClicked) {
            onExitClicked()
            onReset()
        }
    }

    LaunchedEffect(countdown) {
        val soundRes = when (countdown) {
            1, 2, 3 -> R.raw.beep
            null -> R.raw.start
            else -> null
        }
        soundRes?.let { Sound.playSound(context, it) }
    }

    if (gameMode == GameMode.BOT && isAppInForeground && !someoneWon) {
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
                val botConfig = FirebaseUtils.getBotConfig(Firebase.remoteConfig, botLevel)
                val (minDelay, maxDelay, accuracy) = botConfig

                delay(Random.nextLong(minDelay, maxDelay))
                val options = gameState.options.orEmpty()

                val botChoice = if (Random.nextFloat() < accuracy)
                    options.firstOrNull { it.answer }
                else
                    options.filterNot { it.answer }.randomOrNull()

                botChoice?.let { onBotAnswer(it.option, true) }
            }
        }
    }
}