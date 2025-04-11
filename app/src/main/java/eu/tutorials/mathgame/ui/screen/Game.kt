package eu.tutorials.mathgame.ui.screen

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.ui.component.game.GameSection
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import eu.tutorials.mathgame.util.Sound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Game(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = hiltViewModel(),
    onExitClicked: () -> Unit,
    gameMode: GameMode,
    botLevel: BotLevel?
) {
    Log.d("Hiii", "gameMode: $gameMode, botLevel: $botLevel")
    val context = LocalContext.current
    val gameState = gameViewModel.gameState.collectAsState().value
    val countdown = gameState.countdown

    LaunchedEffect(gameState.isExitClicked) {
        if(gameState.isExitClicked) {
            onExitClicked()
            gameViewModel.onEvent(GameEvent.OnReset)
        }
    }

    LaunchedEffect(countdown) {
        val soundRes = when (countdown) {
            3 -> R.raw.beep
            2 -> R.raw.beep
            1 -> R.raw.beep
            null -> R.raw.start
            else -> null
        }

        soundRes?.let {
            Sound.playSound(context, it)
        }
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

                val options = gameState.options ?: emptyList()
                if (options.isNotEmpty()) {
                    val botChoice = if (Random.nextFloat() < accuracy) {
                        options.firstOrNull { it.answer }
                    } else {
                        options.filterNot { it.answer }.randomOrNull()
                    }

                    botChoice?.let {
                        gameViewModel.onEvent(
                            GameEvent.OnOptionButtonClicked(
                                it.option,
                                isBlueSection = true
                            )
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(100.dp)
                    .clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100))
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(-90f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = gameState.redScore.toString(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = gameState.blueScore.toString(),
                        color = Color.Blue
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                GameSection(
                    operands = gameState.operands ?: emptyList(),
                    operation = gameState.operation,
                    options = gameState.options ?: emptyList(),
                    rotation = 180f,
                    color = Color(0xFFD8E0FE),
                    borderColor = MaterialTheme.colorScheme.inversePrimary,
                    selectedOption = gameState.selectedBlueOption,
                    enabled = if (gameMode == GameMode.NORMAL) {
                        (gameState.selectedBlueOption == null) && (gameState.selectedRedOption == null)
                    } else false,
                    onOptionClick = {
                        gameViewModel.onEvent(
                            GameEvent.OnOptionButtonClicked(
                                it,
                                isBlueSection = true
                            )
                        )
                    }
                )

                GameSection(
                    operands = gameState.operands ?: emptyList(),
                    operation = gameState.operation,
                    options = gameState.options ?: emptyList(),
                    rotation = 0f,
                    color = Color(0xFFFECCD1),
                    borderColor = Color.Red,
                    selectedOption = gameState.selectedRedOption,
                    enabled = (gameState.selectedBlueOption == null) && (gameState.selectedRedOption == null),
                    onOptionClick = {
                        gameViewModel.onEvent(
                            GameEvent.OnOptionButtonClicked(
                                it,
                                isBlueSection = false
                            )
                        )
                    }
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .rotate(-90f),
            ) {
                Button(
                    onClick = {
                        gameViewModel.onEvent(GameEvent.OnExitClicked)
                    },
                    shape = RoundedCornerShape(topEndPercent = 100, topStartPercent = 100),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "EXIT", color = Color.Black)
                }
            }
        }

        if (countdown != null) {
            val scale = remember { Animatable(0.5f) }

            LaunchedEffect(countdown) {
                scale.snapTo(0.5f)
                scale.animateTo(
                    targetValue = 1.5f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = countdown.toString(),
                    fontSize = 100.sp,
                    color = Color.White,
                    modifier = Modifier.scale(scale.value)
                )
            }
        }
    }
}