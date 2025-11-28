package eu.tutorials.mathgame.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import eu.tutorials.mathgame.data.model.BotConfig
import eu.tutorials.mathgame.data.model.BotLevel

object FirebaseUtils {
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