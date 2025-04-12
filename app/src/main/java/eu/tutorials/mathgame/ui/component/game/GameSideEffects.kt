package eu.tutorials.mathgame.ui.component.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.GameState
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
    onReset: () -> Unit
) {
    val countdown = gameState.countdown

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

    if (gameMode == GameMode.BOT) {
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
                val (minDelay, maxDelay, accuracy) = when (botLevel) {
                    BotLevel.HARD -> Triple(1500L, 2000L, 0.6f)
                    BotLevel.MEDIUM -> Triple(2000L, 2500L, 0.5f)
                    BotLevel.EASY -> Triple(2500L, 3000L, 0.4f)
                    null -> Triple(2000L, 2500L, 0.5f)
                }

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