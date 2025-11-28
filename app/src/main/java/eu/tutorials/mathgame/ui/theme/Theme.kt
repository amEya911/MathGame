package eu.tutorials.mathgame.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DarkAppColors = AppColors(
    textBlack = Color(0xFF000000),
    textWhite = Color(0xFFFFFFFF),
    normalModeImageColor = Color(0xFFFFFFFF),

    normalModeBackground = Color(0xFFE0D7FF),
    normalModeSurface = Color(0xFFF7FEF7),
    normalModeButton = Color(0xFFB619AD),
    normalModeText = Color(0xFF330033),
    normalModeTopBackground = Color(0xFF9EA3F7),

    easyColor = Color(0xFF4CAF50),
    mediumColor = Color(0xFFFFEB3B),
    hardColor = Color(0xFFFF0000),

    primaryColor = Color(0xFFFF0000),
    primaryInverseColor = Color(0xFF0000FF),

    correctAnswerColor = Color(0xFF00C853),
    wrongAnswerColor = Color(0xFF444444),

    botModeBackground = Color(0xFF888888),
    botModeBox = Color(0xFF444444)
)

val LightAppColors = DarkAppColors

val LightAppTypography = AppTypography(
    xxxSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    xxSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp
    ),
    xSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    small = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    medium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    large = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    xLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    xxLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    xxxLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    xxxxLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),
    xxxxxLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 100.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),
)

val DarkAppTypography = LightAppTypography

val LocalAppTypography = compositionLocalOf { LightAppTypography }

val LocalAppColors = compositionLocalOf { LightAppColors }

//private val DarkColorScheme = darkColorScheme(
//    background = normalModeBackground,
//    surface = normalModeSurface,
//    secondary = normalModeButton,
//    onSecondary = normalModeText,
//    tertiary = normalModeTopBackground,
//    primaryContainer = easyColor,
//    secondaryContainer = mediumColor,
//    tertiaryContainer = hardColor,
//    primary = primaryColor,
//    inversePrimary = primaryInverseColor,
//    error = wrongAnswerColor,
//    errorContainer = correctAnswerColor,
//    inverseSurface = botModeBox,
//    surfaceVariant = botModeBackground
//)
//
//private val LightColorScheme = lightColorScheme(
//    background = normalModeBackground,
//    surface = normalModeSurface,
//    secondary = normalModeButton,
//    onSecondary = normalModeText,
//    tertiary = normalModeTopBackground,
//    primaryContainer = easyColor,
//    secondaryContainer = mediumColor,
//    tertiaryContainer = hardColor,
//    primary = primaryColor,
//    inversePrimary = primaryInverseColor,
//    error = wrongAnswerColor,
//    errorContainer = correctAnswerColor,
//    inverseSurface = botModeBox,
//    surfaceVariant = botModeBackground
//)

@Composable
fun MathGameTheme(
    //remoteColors: RemoteColors?,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
//    val colorSchemeOverride = remoteColors?.toColorScheme()
//    val colorScheme = when {
//        colorSchemeOverride != null -> colorSchemeOverride
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    val customColors = if (darkTheme) DarkAppColors else LightAppColors
    val customTypography = if (darkTheme) DarkAppTypography else LightAppTypography

    CompositionLocalProvider(
        LocalAppColors provides customColors,
        LocalAppTypography provides customTypography
    ) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
}

object AppTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
}