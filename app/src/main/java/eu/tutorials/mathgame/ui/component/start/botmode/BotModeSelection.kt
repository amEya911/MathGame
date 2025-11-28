package eu.tutorials.mathgame.ui.component.start.botmode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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