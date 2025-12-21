package eu.tutorials.mathgame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.mathgame.data.datasource.remote.AnalyticsLogger
import eu.tutorials.mathgame.data.datasource.remote.LogEvents
import eu.tutorials.mathgame.navigation.AppNavGraph
import eu.tutorials.mathgame.ui.theme.MathGameTheme
import eu.tutorials.mathgame.ui.viewmodel.ThemeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var analyticsLogger: AnalyticsLogger

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                analyticsLogger.log(LogEvents.APP_OPENED)
                themeViewModel.loadTheme()
            }

            MathGameTheme(colors = themeViewModel.colors) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph()
                }
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStop() {
        super.onStop()
        analyticsLogger.log(LogEvents.CLOSE_APP)
    }
}

