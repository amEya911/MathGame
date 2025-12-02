package eu.tutorials.mathgame.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val textBlack: Color,
    val textWhite: Color,
    val backGroundWhite: Color,
    val normalModeImageColor: Color,
    val sliderColor: Color,
    val normalModeBackground: Color,
    val normalModeSurface: Color,
    val normalModeButton: Color,
    val normalModeText: Color,
    val normalModeTopBackground: Color,
    val easyColor: Color,
    val mediumColor: Color,
    val hardColor: Color,
    val playerOnePrimary: Color,
    val playerOneSecondary: Color,
    val playerTwoPrimary: Color,
    val playerTwoSecondary: Color,
    val correctAnswerColor: Color,
    val wrongAnswerColor: Color,
    val botModeBackground: Color,
    val botModeBox: Color,

    val confettiColors: ConfettiColors
)

data class ConfettiColors(
    val purple: Color,
    val blue: Color,
    val green: Color,
    val yellow: Color,
    val red: Color
)