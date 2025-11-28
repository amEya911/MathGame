package eu.tutorials.mathgame.ui.component.start.botmode

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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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