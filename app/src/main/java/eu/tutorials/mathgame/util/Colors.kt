package eu.tutorials.mathgame.util

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import eu.tutorials.mathgame.data.model.ThemeDto
import eu.tutorials.mathgame.ui.theme.AppColors
import eu.tutorials.mathgame.ui.theme.ConfettiColors

object Colors {
    fun parseColor(
        hex: String?,
        default: Color
    ): Color {
        return try {
            if (hex.isNullOrBlank()) default
            else Color(hex.toColorInt())
        } catch (_: Exception) {
            default
        }
    }

    fun ThemeDto.toAppColors(default: AppColors): AppColors {
        return default.copy(
            textBlack = parseColor(textBlack, default.textBlack),
            textWhite = parseColor(textWhite, default.textWhite),
            backGroundWhite = parseColor(backgroundWhite, default.backGroundWhite),

            normalModeBackground = parseColor(normalMode.background, default.normalModeBackground),
            normalModeSurface = parseColor(normalMode.surface, default.normalModeSurface),
            normalModeButton = parseColor(normalMode.button, default.normalModeButton),
            normalModeText = parseColor(normalMode.text, default.normalModeText),
            normalModeTopBackground = parseColor(
                normalMode.topBackground,
                default.normalModeTopBackground
            ),

            easyColor = parseColor(difficulty.easy, default.easyColor),
            mediumColor = parseColor(difficulty.medium, default.mediumColor),
            hardColor = parseColor(difficulty.hard, default.hardColor),

            playerOnePrimary = parseColor(players.playerOne.primary, default.playerOnePrimary),
            playerOneSecondary = parseColor(players.playerOne.secondary, default.playerOneSecondary),
            playerTwoPrimary = parseColor(players.playerTwo.primary, default.playerTwoPrimary),
            playerTwoSecondary = parseColor(players.playerTwo.secondary, default.playerTwoSecondary),

            correctAnswerColor = parseColor(answers.correct, default.correctAnswerColor),
            wrongAnswerColor = parseColor(answers.wrong, default.wrongAnswerColor),

            botModeBackground = parseColor(botMode.background, default.botModeBackground),
            botModeBox = parseColor(botMode.box, default.botModeBox),

            confettiColors = ConfettiColors(
                purple = parseColor(confetti.purple, default.confettiColors.purple),
                blue = parseColor(confetti.blue, default.confettiColors.blue),
                green = parseColor(confetti.green, default.confettiColors.green),
                red = parseColor(confetti.red, default.confettiColors.red),
                yellow = parseColor(confetti.yellow, default.confettiColors.yellow)
            )
        )
    }


}