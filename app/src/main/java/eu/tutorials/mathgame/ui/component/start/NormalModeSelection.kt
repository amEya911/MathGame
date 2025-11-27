package eu.tutorials.mathgame.ui.component.start

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.theme.botModeBackground
import eu.tutorials.mathgame.ui.theme.primaryColor
import eu.tutorials.mathgame.ui.viewmodel.StartViewModel
import eu.tutorials.mathgame.util.FirebaseUtils

@Composable
fun NormalModeSelection(
    navigator: Navigator,
    startViewModel: StartViewModel,
    startState: StartState
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.botModeBackground)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // 🎯 Game Title
            Text(
                text = "MATH QUIZ",
                style = AppTheme.typography.xxxxLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🧾 Rules Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = buildString {
                            append("The first player to solve the task gets a point. ")
                            append("For any wrong answer, the opponent gets a point.")
                        },
                        style = AppTheme.typography.medium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            RoundsHeader(startState)

            Spacer(modifier = Modifier.height(12.dp))

            BotLevelSlider(
                sliderPosition = startState.levelSliderPosition,
                onValueChange = {
                    startViewModel.onEvent(StartEvent.ChangeLevelSliderPosition(it))
                },
                sliderHeight = 45.dp,
                thumbHeight = 45.dp,
                trackHeight = 30.dp,
                padding = 16.dp,
                endValue = 19f,
                color = AppTheme.colors.primaryColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🎮 Buttons
            StartPageButton(
                icon = painterResource(id = R.drawable.user),
                text = "FRIEND",
                subText = "Play with your buddy!",
                color = AppTheme.colors.normalModeButton,
                onClick = {
                    startViewModel.onEvent(StartEvent.OnNormalModeClicked(navigator))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            StartPageButton(
                icon = painterResource(id = R.drawable.robot),
                text = "BOT",
                subText = "Challenge the AI",
                color = AppTheme.colors.normalModeTopBackground,
                onClick = {
                    startViewModel.onEvent(StartEvent.OnBotModeClicked)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RoundsHeader(startState: StartState) {
    val rounds = startState.levelSliderPosition.toInt().inc()

    // Track previous value to know direction (increase/decrease)
    var previousRounds by remember { mutableStateOf(rounds) }
    val isIncreasing = rounds > previousRounds

    LaunchedEffect(rounds) {
        previousRounds = rounds
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Select number of rounds",
            style = AppTheme.typography.large.copy(
                fontWeight = FontWeight.Bold
            ),
        )

        AnimatedContent(
            targetState = rounds,
            transitionSpec = {
                if (isIncreasing) {
                    // Number increased → old goes left, new comes from right
                    (slideInHorizontally(
                        animationSpec = tween(250)
                    ) { fullWidth -> fullWidth } + fadeIn()) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { fullWidth -> -fullWidth } + fadeOut())
                } else {
                    // Number decreased → old goes right, new comes from left
                    (slideInHorizontally(
                        animationSpec = tween(250)
                    ) { fullWidth -> -fullWidth } + fadeIn()) togetherWith
                            (slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { fullWidth -> fullWidth } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "rounds_animation"
        ) { value ->
            Text(
                text = value.toString(),
                style = AppTheme.typography.large.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.graphicsLayer {
                    cameraDistance = 8 * density
                    rotationY = if (isIncreasing) -10f else 10f
                }
            )
        }
    }
}



@Composable
fun StartPageButton(
    icon: Painter,
    text: String,
    subText: String,
    color: Color,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = spring(),
        label = "scale"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (pressed) color.copy(alpha = 0.85f) else color,
        animationSpec = spring(),
        label = "color"
    )

    Button(
        onClick = {
            pressed = true
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
            onClick()
            pressed = false
        },
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                Text(
                    text = "PLAY VS",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp
                )
                Text(
                    text = text,
                    color = Color.White,
                    style = AppTheme.typography.xxLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = subText,
                    color = Color.White.copy(alpha = 0.8f),
                    style = AppTheme.typography.xSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}
