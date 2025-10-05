package eu.tutorials.mathgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.mathgame.data.model.RemoteColors
import eu.tutorials.mathgame.data.model.toColorScheme
import eu.tutorials.mathgame.navigation.AppNavGraph
import eu.tutorials.mathgame.ui.theme.MathGameTheme
import eu.tutorials.mathgame.util.FirebaseUtils

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        setContent {
            var remoteColors by remember { mutableStateOf<RemoteColors?>(null) }

            LaunchedEffect(Unit) {
                remoteConfig.fetchAndActivate().addOnCompleteListener {
                    if (it.isSuccessful) {
                        remoteColors = FirebaseUtils.getRemoteColors(remoteConfig)
                    }
                }
            }
            MathGameTheme (
                colorSchemeOverride = remoteColors?.toColorScheme()
            ){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

