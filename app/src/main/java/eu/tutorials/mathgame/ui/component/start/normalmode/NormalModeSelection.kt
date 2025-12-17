package eu.tutorials.mathgame.ui.component.start.normalmode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.data.event.StartEvent
import eu.tutorials.mathgame.data.state.StartState
import eu.tutorials.mathgame.navigation.Navigator
import eu.tutorials.mathgame.ui.component.common.Slider
import eu.tutorials.mathgame.ui.theme.AppTheme
import eu.tutorials.mathgame.ui.viewmodel.StartViewModel

@Composable
fun NormalModeSelection(
    navigator: Navigator,
    startViewModel: StartViewModel,
    startState: StartState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                listOf(
                    AppTheme.colors.normalModeTopBackground,
                    AppTheme.colors.botModeBackground,
                )
            ))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "MATH QUIZ",
                style = AppTheme.typography.xxxxLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = AppTheme.colors.textWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.backGroundWhite.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildString {
                            append("The first player to solve the task gets a point. ")
                            append("For any wrong answer, the opponent gets a point. ")
                            append("Adjust the slider to change the number of rounds.")
                        },
                        style = AppTheme.typography.medium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        color = AppTheme.colors.textBlack,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            RoundsHeader(startState)

            Spacer(modifier = Modifier.height(12.dp))

            Slider(
                sliderPosition = startState.levelSliderPosition,
                onValueChange = {
                    startViewModel.onEvent(StartEvent.ChangeLevelSliderPosition(it))
                },
                sliderHeight = 45.dp,
                thumbHeight = 45.dp,
                trackHeight = 30.dp,
                padding = 12.dp,
                endValue = 19f,
                color = AppTheme.colors.playerOnePrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

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