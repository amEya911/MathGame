package eu.tutorials.mathgame.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
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

private val LightColorScheme = lightColorScheme(
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

@Composable
fun MathGameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    colorSchemeOverride: ColorScheme? = null,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        colorSchemeOverride != null -> colorSchemeOverride
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}