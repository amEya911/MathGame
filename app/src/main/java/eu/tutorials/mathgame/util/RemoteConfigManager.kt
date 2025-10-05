package eu.tutorials.mathgame.util

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

object RemoteConfigManager {
    suspend fun init() {
        val config = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        config.setConfigSettingsAsync(configSettings).await()
        config.fetchAndActivate().await()
    }

    fun get() = Firebase.remoteConfig
}
