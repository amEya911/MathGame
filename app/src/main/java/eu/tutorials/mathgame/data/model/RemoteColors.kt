package eu.tutorials.mathgame.data.model

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

data class RemoteColors(
    val normalModeBackground: Color,
    val normalModeSurface: Color,
    val normalModeButton: Color,
    val normalModeText: Color,
    val normalModeTopBackground: Color,
    val easyColor: Color,
    val mediumColor: Color,
    val hardColor: Color,
    val primaryColor: Color,
    val primaryInverseColor: Color,
    val correctAnswerColor: Color,
    val wrongAnswerColor: Color,
    val botModeBackground: Color,
    val botModeBox: Color
)

fun RemoteColors.toColorScheme(): ColorScheme {
    return lightColorScheme(
        background = normalModeBackground,
        surface = normalModeSurface,
        secondary = normalModeButton,
        onSecondary = normalModeText,
        tertiary = normalModeTopBackground,
        primaryContainer = easyColor,
        secondaryContainer = mediumColor,
        tertiaryContainer = hardColor,
        primary = primaryColor,
        inversePrimary = primaryInverseColor,
        error = wrongAnswerColor,
        errorContainer = correctAnswerColor,
        inverseSurface = botModeBox,
        surfaceVariant = botModeBackground
    )
}
