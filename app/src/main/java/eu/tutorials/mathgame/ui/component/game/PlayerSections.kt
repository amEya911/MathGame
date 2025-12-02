package eu.tutorials.mathgame.ui.component.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.model.PlayerSectionSpec
import eu.tutorials.mathgame.data.state.GameState
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel

@Composable
fun PlayerSections(
    gameState: GameState,
    gameViewModel: GameViewModel,
) {
    val sections = listOf(
        PlayerSectionSpec(
            isBlue = true,
            rotation = 180f,
            color = AppTheme.colors.playerTwoSecondary,
            borderColor = AppTheme.colors.playerTwoPrimary
        ),
        PlayerSectionSpec(
            isBlue = false,
            rotation = 0f,
            color = AppTheme.colors.playerOneSecondary,
            borderColor = AppTheme.colors.playerOnePrimary
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        sections.forEach { spec ->
            val selectedOption =
                if (spec.isBlue) gameState.selectedBlueOption else gameState.selectedRedOption

            val enabled = if (spec.isBlue) {
                gameState.gameMode == GameMode.NORMAL &&
                        gameState.selectedBlueOption == null &&
                        gameState.selectedRedOption == null
            } else {
                gameState.selectedBlueOption == null &&
                        gameState.selectedRedOption == null
            }

            GameSection(
                operands = gameState.operands.orEmpty(),
                operation = gameState.operation,
                options = gameState.options.orEmpty(),
                rotation = spec.rotation,
                color = spec.color,
                borderColor = spec.borderColor,
                selectedOption = selectedOption,
                enabled = enabled,
                onOptionClick = { option ->
                    gameViewModel.onEvent(
                        GameEvent.OnOptionButtonClicked(
                            selectedOption = option,
                            isBlueSection = spec.isBlue
                        )
                    )
                },
                onOptionPositioned = { rect ->
                    gameViewModel.onEvent(GameEvent.ChangeSelectedButtonRect(rect))
                }
            )
        }
    }
}