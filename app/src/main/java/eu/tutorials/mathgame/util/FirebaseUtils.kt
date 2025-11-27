package eu.tutorials.mathgame.util

import androidx.compose.ui.graphics.Color
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import eu.tutorials.mathgame.data.model.BotConfig
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.MaxWinningPoints
import eu.tutorials.mathgame.data.model.RemoteColors
import androidx.core.graphics.toColorInt

object FirebaseUtils {
    fun getRemoteColors(remoteConfig: FirebaseRemoteConfig): RemoteColors? {
        fun getColor(key: String, default: Color = Color.Black): Color? {
            val hex = remoteConfig.getString(key)
            return try {
                if (hex.isBlank()) null else Color(hex.toColorInt())
            } catch (e: Exception) {
                null
            }
        }

        val normalModeBackground = getColor("normalModeBackground") ?: return null
        val normalModeSurface = getColor("normalModeSurface") ?: return null
        val normalModeButton = getColor("normalModeButton") ?: return null
        val normalModeText = getColor("normalModeText") ?: return null
        val normalModeTopBackground = getColor("normalModeTopBackground") ?: return null
        val easyColor = getColor("easyColor") ?: return null
        val mediumColor = getColor("mediumColor") ?: return null
        val hardColor = getColor("hardColor") ?: return null
        val primaryColor = getColor("primaryColor") ?: return null
        val primaryInverseColor = getColor("primaryInverseColor") ?: return null
        val correctAnswerColor = getColor("correctAnswerColor") ?: return null
        val wrongAnswerColor = getColor("wrongAnswerColor") ?: return null
        val botModeBackground = getColor("botModeBackground") ?: return null
        val botModeBox = getColor("botModeBox") ?: return null

        return RemoteColors(
            normalModeBackground, normalModeSurface, normalModeButton,
            normalModeText, normalModeTopBackground, easyColor, mediumColor,
            hardColor, primaryColor, primaryInverseColor, correctAnswerColor,
            wrongAnswerColor, botModeBackground, botModeBox
        )
    }


    fun getBotConfig(remoteConfig: FirebaseRemoteConfig, botLevel: BotLevel?): BotConfig {
        val levelKey = when (botLevel) {
            BotLevel.EASY -> "easy"
            BotLevel.MEDIUM -> "medium"
            BotLevel.HARD -> "hard"
            null -> "medium"
        }

        val defaultConfig = when (botLevel) {
            BotLevel.EASY -> BotConfig(2500L, 3000L, 0.4f)
            BotLevel.MEDIUM, null -> BotConfig(2000L, 2500L, 0.5f)
            BotLevel.HARD -> BotConfig(1500L, 2000L, 0.6f)
        }

        val minDelay = remoteConfig.getLong("bot_${levelKey}_minDelay")
            .takeIf { it > 0 } ?: defaultConfig.minDelay

        val maxDelay = remoteConfig.getLong("bot_${levelKey}_maxDelay")
            .takeIf { it > 0 } ?: defaultConfig.maxDelay

        val accuracy = remoteConfig.getDouble("bot_${levelKey}_accuracy")
            .toFloat().takeIf { it in 0.0..1.0 } ?: defaultConfig.accuracy

        return BotConfig(minDelay, maxDelay, accuracy)
    }
}