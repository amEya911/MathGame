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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.model.BotLevel
import kotlin.math.roundToInt

@Composable
fun BotModeSelection(
    onBotLevelSelected: (BotLevel) -> Unit,
    onBackClicked: () -> Unit
) {
    val levels = listOf(BotLevel.EASY, BotLevel.MEDIUM, BotLevel.HARD)
    var sliderPosition by remember { mutableStateOf(0f) }
    var previousLevel by remember { mutableStateOf(BotLevel.EASY) }

    val selectedLevel = levels[sliderPosition.roundToInt()]
    val isLevelIncreasing = selectedLevel.ordinal > previousLevel.ordinal

    val painterId = when (selectedLevel) {
        BotLevel.EASY -> R.drawable.easy
        BotLevel.MEDIUM -> R.drawable.medium
        BotLevel.HARD -> R.drawable.hard
    }

    val color = when (selectedLevel) {
        BotLevel.EASY -> MaterialTheme.colorScheme.primaryContainer
        BotLevel.MEDIUM -> MaterialTheme.colorScheme.secondaryContainer
        BotLevel.HARD -> MaterialTheme.colorScheme.tertiaryContainer
    }

    LaunchedEffect(sliderPosition) {
        previousLevel = selectedLevel
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        BotImage(painterId, isLevelIncreasing)

        BotLevelCard(
            selectedLevel = selectedLevel,
            color = color,
            sliderPosition = sliderPosition,
            onSliderChange = { sliderPosition = it },
            onPlayClicked = { onBotLevelSelected(selectedLevel) }
        )

        BackButton(onBackClicked)
    }
}

@Composable
fun BotImage(painterId: Int, isLevelIncreasing: Boolean) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .offset(y = 20.dp)
            .zIndex(1f)
            .background(Color.White, shape = CircleShape),
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
                modifier = Modifier.size(80.dp)
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
            .background(MaterialTheme.colorScheme.inverseSurface, shape = MaterialTheme.shapes.medium)
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
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotLevelSlider(sliderPosition, onSliderChange, color)

            Text(
                text = "Drag to adjust\ndifficulty",
                color = Color.Black,
                fontSize = 25.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
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
    color: Color
) {
    Slider(
        value = sliderPosition,
        onValueChange = onValueChange,
        valueRange = 0f..2f,
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .height(60.dp),
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
                    .size(48.dp)
                    .background(color, shape = CircleShape)
                    .border(12.dp, Color.White, CircleShape)
            )
        },
        track = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(Color.LightGray, RoundedCornerShape(50))
                    .border(8.dp, Color.White, RoundedCornerShape(50))
            )
        }
    )
}

@Composable
fun PlayButton(color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text("PLAY", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BackButton(onBackClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable { onBackClicked() }
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
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

