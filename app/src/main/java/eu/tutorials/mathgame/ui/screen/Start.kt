package eu.tutorials.mathgame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.ui.component.start.BotModeSelection
import eu.tutorials.mathgame.ui.component.start.NormalModeSelection
import eu.tutorials.mathgame.ui.viewmodel.StartViewModel

@Composable
fun Start(
    startViewModel: StartViewModel = hiltViewModel(),
    onStartClicked: (GameMode, BotLevel?) -> Unit
) {
    val startState = startViewModel.startState.collectAsState().value

    LaunchedEffect(startState.isGameStartTriggered) {
        if (startState.isGameStartTriggered) {
            onStartClicked(
                startState.gameMode,
                startState.botLevel
            )
            startViewModel.onEvent(StartEvent.OnReset)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        when (startState.gameMode) {
            GameMode.NORMAL -> {
                NormalModeSelection(
                    onNormalModeClicked = {
                        startViewModel.onEvent(StartEvent.OnNormalModeClicked)
                    },
                    onBotModeClicked = {
                        startViewModel.onEvent(StartEvent.OnBotModeClicked)
                    },
                    remoteConfig = startViewModel.config
                )
            }

            GameMode.BOT -> {
                BotModeSelection(
                    onBotLevelSelected = { level ->
                        startViewModel.onEvent(StartEvent.OnBotLevelSelected(level))
                    },
                    onBackClicked = {
                        startViewModel.onEvent(StartEvent.OnBackClicked)
                    }
                )
            }
        }
    }
}
