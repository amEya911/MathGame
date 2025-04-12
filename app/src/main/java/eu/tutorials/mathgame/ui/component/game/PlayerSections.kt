package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel

@Composable
fun PlayerSections(
    gameState: GameState,
    gameMode: GameMode,
    gameViewModel: GameViewModel,
    onOptionPositioned: (Rect) -> Unit
) {
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
            },
            onOptionPositioned = onOptionPositioned
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
            },
            onOptionPositioned = onOptionPositioned
        )
    }
}