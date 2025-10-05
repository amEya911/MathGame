package eu.tutorials.mathgame.ui.component.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import eu.tutorials.mathgame.R
import eu.tutorials.mathgame.util.FirebaseUtils

@Composable
fun NormalModeSelection(
    onNormalModeClicked: () -> Unit,
    onBotModeClicked: () -> Unit,
    remoteConfig: FirebaseRemoteConfig
) {
    val maxWinningPoints = FirebaseUtils.getMaxWinningPoints(remoteConfig).maxPoints

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.tertiary)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MATH QUIZ",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .height(600.dp)
                    .width(400.dp)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "The first player to solve the task gets a point. For any wrong answer the opponent gets a point." +
                                (maxWinningPoints?.takeIf { it != 0L }
                                    ?.let { " First to $it points wins." } ?: ""),
                        fontSize = 24.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(48.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    StartPageButton(
                        icon = painterResource(id = R.drawable.user),
                        text = "FRIEND",
                        onClick = onNormalModeClicked
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StartPageButton(
                        icon = painterResource(id = R.drawable.robot),
                        text = "BOT",
                        onClick = onBotModeClicked
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StartPageButton(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = icon,
                contentDescription = "Bot",
                modifier = Modifier.size(75.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Column {
                Text(
                    text = "PLAY VS.",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 17.sp
                )
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
