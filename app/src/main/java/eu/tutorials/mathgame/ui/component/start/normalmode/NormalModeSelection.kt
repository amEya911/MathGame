package eu.tutorials.mathgame.ui.component.start.normalmode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

            Text(
                text = "MATH QUIZ",
                style = AppTheme.typography.xxxxLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = AppTheme.colors.textWhite,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

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