package eu.tutorials.mathgame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.component.start.BotModeSelection
import eu.tutorials.mathgame.ui.component.start.NormalModeSelection
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.viewmodel.StartViewModel

@Composable
fun Start(
    startViewModel: StartViewModel,
    startState: StartState,
    navigator: Navigator,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.normalModeBackground),
        contentAlignment = Alignment.Center
    ) {
        when (startState.gameMode) {
            GameMode.NORMAL -> {
                NormalModeSelection(
                    startViewModel = startViewModel,
                    navigator = navigator,
                    startState = startState
                )
            }

            GameMode.BOT -> {
                BotModeSelection(
                    startState = startState,
                    onEvent = startViewModel::onEvent,
                    navigator = navigator,
                )
            }
        }
    }
}
