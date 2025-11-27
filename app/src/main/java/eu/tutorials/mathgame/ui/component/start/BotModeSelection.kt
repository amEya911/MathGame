package eu.tutorials.mathgame.ui.component.start

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun BotModeSelection(
    startState: StartState,
    onEvent: (StartEvent) -> Unit,
    navigator: Navigator
) {
    val sliderPosition = startState.sliderPosition

    val levels = listOf(BotLevel.EASY, BotLevel.MEDIUM, BotLevel.HARD)
    val selectedLevel = levels[sliderPosition.roundToInt()]

    val painterId = when (selectedLevel) {
        BotLevel.EASY -> R.drawable.easy
        BotLevel.MEDIUM -> R.drawable.medium
        BotLevel.HARD -> R.drawable.hard
    }

    val color = when (selectedLevel) {
        BotLevel.EASY -> AppTheme.colors.easyColor
        BotLevel.MEDIUM -> AppTheme.colors.mediumColor
        BotLevel.HARD -> AppTheme.colors.hardColor
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.botModeBackground)
            .verticalScroll(rememberScrollState())
    ) {
        BotImage(painterId, color, startState)

        BotLevelCard(
            selectedLevel = selectedLevel,
            color = color,
            sliderPosition = sliderPosition,
            onSliderChange = {
                onEvent(StartEvent.ChangeSliderPosition(it))
                             },
            onPlayClicked = {
                onEvent(StartEvent.OnBotLevelSelected(selectedLevel, navigator))
            }
        )

        BackButton(
            onBackClicked = {
                onEvent(StartEvent.OnBackClicked)
            }
        )
    }
}

@Composable
fun BotImage(painterId: Int, color: Color, startState: StartState) {
    val isLevelIncreasing = startState.isLevelIncreasing
    Box(
        modifier = Modifier
            .size(100.dp)
            .offset(y = 20.dp)
            .zIndex(1f)
            .background(color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = painterId,
            transitionSpec = {
                val direction = if (isLevelIncreasing) 1 else -1
                slideInVertically(tween(300)) { it * direction } togetherWith
                        slideOutVertically(tween(300)) { -it * direction }
            },
            label = "BotLevelImageAnimation"
        ) { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = "Bot Level Icon",
                modifier = Modifier.size(80.dp),
            )
        }
    }
}

@Composable
fun BotLevelCard(
    selectedLevel: BotLevel,
    color: Color,
    sliderPosition: Float,
    onSliderChange: (Float) -> Unit,
    onPlayClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(500.dp)
            .width(350.dp)
            .background(AppTheme.colors.botModeBox, shape = MaterialTheme.shapes.medium)
            .zIndex(-1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = selectedLevel,
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) 1 else -1
                    slideInVertically(tween(300)) { it * direction } togetherWith
                            slideOutVertically(tween(300)) { -it * direction }
                },
                label = "BotLevelTextAnimation"
            ) { level ->
                Text(
                    text = level.name,
                    style = AppTheme.typography.xxxLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = color
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotLevelSlider(
                sliderPosition = sliderPosition,
                onValueChange = onSliderChange,
                color = color
            )

            Text(
                text = "Drag to adjust\ndifficulty",
                color = Color.Black,
                style = AppTheme.typography.xLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            PlayButton(color = color, onClick = onPlayClicked)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BotLevelSlider(
    sliderPosition: Float,
    onValueChange: (Float) -> Unit,
    sliderHeight: Dp = 60.dp,
    thumbHeight: Dp = 48.dp,
    trackHeight: Dp = 36.dp,
    padding: Dp = 32.dp,
    endValue: Float = 2f,
    color: Color
) {
    Slider(
        value = sliderPosition,
        onValueChange = onValueChange,
        valueRange = 0f..endValue,
        modifier = Modifier
            .padding(horizontal = padding)
            .fillMaxWidth()
            .height(sliderHeight),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = Color.LightGray,
            inactiveTrackColor = Color.Gray,
            disabledThumbColor = color,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        ),
        thumb = {
            Box(
                modifier = Modifier
                    .size(thumbHeight)
                    .background(color, shape = CircleShape)
                    .border(12.dp, Color.White, CircleShape)
            )
        },
        track = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .background(Color.LightGray, RoundedCornerShape(50))
                    .border(8.dp, Color.White, RoundedCornerShape(50))
            )
        }
    )
}

@Composable
fun PlayButton(color: Color, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text("PLAY", style = AppTheme.typography.large.copy(
            fontWeight = FontWeight.Bold
        ),)
    }
}

@Composable
fun BackButton(onBackClicked: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onBackClicked()
            }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Back",
            color = Color.Black,
            style = AppTheme.typography.large.copy(
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

