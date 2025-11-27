package eu.tutorials.mathgame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.mathgame.navigation.AppNavGraph
import eu.tutorials.mathgame.navigation.RootNavGraph
import eu.tutorials.mathgame.ui.theme.MathGameTheme
import eu.tutorials.mathgame.ui.viewmodel.ConfigViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
//            val configViewModel: ConfigViewModel = hiltViewModel()
//            val configState by configViewModel.remoteState.collectAsState()
//            val remoteColors = configState.remoteColors
//
//            if (remoteColors == null) {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            } else {
                MathGameTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        //AppNavGraph(modifier = Modifier.padding(innerPadding))
                        RootNavGraph(modifier = Modifier.padding(innerPadding))
                    }
                }
            //}
        }
    }
}

