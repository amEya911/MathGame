package eu.tutorials.mathgame.util

import androidx.compose.ui.graphics.Color
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import eu.tutorials.mathgame.data.model.BotConfig
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.MaxWinningPoints
import eu.tutorials.mathgame.data.model.RemoteColors

object FirebaseUtils {
    fun getRemoteColors(remoteConfig: FirebaseRemoteConfig): RemoteColors {
        fun getColor(key: String): Color {
            val hex = remoteConfig.getString(key)
            return try {
                Color(android.graphics.Color.parseColor(hex))
            } catch (e: Exception) {
                Color.Black
            }
        }

        return RemoteColors(
            normalModeBackground = getColor("normalModeBackground"),
            normalModeSurface = getColor("normalModeSurface"),
            normalModeButton = getColor("normalModeButton"),
            normalModeText = getColor("normalModeText"),
            normalModeTopBackground = getColor("normalModeTopBackground"),
            easyColor = getColor("easyColor"),
            mediumColor = getColor("mediumColor"),
            hardColor = getColor("hardColor"),
            primaryColor = getColor("primaryColor"),
            primaryInverseColor = getColor("primaryInverseColor"),
            correctAnswerColor = getColor("correctAnswerColor"),
            wrongAnswerColor = getColor("wrongAnswerColor"),
            botModeBackground = getColor("botModeBackground"),
            botModeBox = getColor("botModeBox")
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

    fun getMaxWinningPoints(remoteConfig: FirebaseRemoteConfig): MaxWinningPoints {
        val maxWinningPoints = remoteConfig.getLong("max_winning_points")
        return MaxWinningPoints(maxWinningPoints)
    }
}