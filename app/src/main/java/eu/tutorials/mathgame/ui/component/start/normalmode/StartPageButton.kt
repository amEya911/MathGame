package eu.tutorials.mathgame.ui.component.start.normalmode

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.mathgame.ui.theme.AppTheme

@Composable
fun StartPageButton(
    icon: Painter,
    text: String,
    subText: String,
    color: Color,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(),
        label = "scale"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (isPressed) color.copy(alpha = 0.85f) else color,
        animationSpec = spring(),
        label = "color"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .scale(scale),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                colorFilter = ColorFilter.tint(AppTheme.colors.normalModeImageColor)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                Text(
                    text = "PLAY VS",
                    color = AppTheme.colors.textWhite.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = text,
                    color = AppTheme.colors.textWhite,
                    style = AppTheme.typography.xxLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subText,
                    color = AppTheme.colors.textWhite.copy(alpha = 0.8f),
                    style = AppTheme.typography.xSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee()
                )
            }
        }
    }
}