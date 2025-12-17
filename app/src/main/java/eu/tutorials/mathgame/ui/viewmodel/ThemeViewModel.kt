package eu.tutorials.mathgame.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.ui.theme.DarkAppColors
import eu.tutorials.mathgame.util.FirebaseUtils
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    var colors by mutableStateOf(DarkAppColors)
        private set

    fun loadTheme() {
        colors = FirebaseUtils.getThemeFromRemoteConfig(remoteConfig)
    }
}
