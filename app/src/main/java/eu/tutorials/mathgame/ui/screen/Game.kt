package eu.tutorials.mathgame.ui.screen

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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.ui.component.game.AnimationExplosion
import eu.tutorials.mathgame.ui.component.game.CountDown
import eu.tutorials.mathgame.ui.component.game.ExitButton
import eu.tutorials.mathgame.ui.component.game.GameSideEffects
import eu.tutorials.mathgame.ui.component.game.PlayerSections
import eu.tutorials.mathgame.ui.component.game.ScoreIndicator
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import eu.tutorials.mathgame.util.AppLifecycle.rememberAppInForeground
import eu.tutorials.mathgame.util.FirebaseUtils
import kotlinx.coroutines.delay

@Composable
fun Game(
    gameViewModel: GameViewModel = hiltViewModel(),
    onExitClicked: () -> Unit,
    gameMode: GameMode,
    botLevel: BotLevel?
) {
    val isAppInForeground = rememberAppInForeground()
    val maxWinningPoints = FirebaseUtils.getMaxWinningPoints(Firebase.remoteConfig).maxPoints

    val context = LocalContext.current
    val gameState = gameViewModel.gameState.collectAsState().value
    val countdown = gameState.countdown

    val isSelected = gameState.selectedBlueOption != null || gameState.selectedRedOption != null
    var circleRadius by remember { mutableStateOf(0.dp) }

    var selectedButtonRect by remember { mutableStateOf<Rect?>(null) }

    val animatedRadius by animateDpAsState(
        targetValue = circleRadius,
        animationSpec = tween(durationMillis = 500),
        label = "circleAnimation"
    )

    GameSideEffects(
        context = context,
        gameState = gameState,
        gameMode = gameMode,
        botLevel = botLevel,
        isSelected = isSelected,
        onExitClicked = onExitClicked,
        updateCircleRadius = { circleRadius = it },
        onBotAnswer = { option, isBlue ->
            gameViewModel.onEvent(GameEvent.OnOptionButtonClicked(option, isBlue))
        },
        onReset = {
            gameViewModel.onEvent(GameEvent.OnReset)
        },
        isAppInForeground = isAppInForeground,
        maxWinningPoints = maxWinningPoints
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            if (isSelected && selectedButtonRect != null) {
                AnimationExplosion(context, gameState, selectedButtonRect, animatedRadius)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(100.dp)
                    .offset(x = (-50).dp)
                    .clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100))
                    .background(Color.White)
            ) {
                ScoreIndicator(gameState)
            }

            PlayerSections(
                gameState = gameState,
                gameMode = gameMode,
                gameViewModel = gameViewModel,
                onOptionPositioned = { rect -> selectedButtonRect = rect }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 40.dp)
                    .rotate(-90f)
            ) {
                ExitButton(gameViewModel)
            }
        }

        if (countdown != null) {
            CountDown(countdown)
        }
    }

    if (maxWinningPoints != null && maxWinningPoints != 0L) {
        if (gameState.blueScore == maxWinningPoints.toInt() || gameState.redScore == maxWinningPoints.toInt()) {
            var showWinnerBox by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(3000)
                showWinnerBox = true
                delay(2000)
                onExitClicked()
            }

            if (showWinnerBox) {
                val backgroundColor = if (gameState.blueScore == maxWinningPoints.toInt())
                    MaterialTheme.colorScheme.inversePrimary
                else
                    MaterialTheme.colorScheme.primary

                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (gameState.blueScore == maxWinningPoints.toInt()) "Blue Wins!" else "Red Wins!",
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}