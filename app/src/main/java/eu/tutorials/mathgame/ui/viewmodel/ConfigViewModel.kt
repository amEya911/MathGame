package eu.tutorials.mathgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.mathgame.data.event.ConfigEvent
import eu.tutorials.mathgame.data.model.RemoteColors
import eu.tutorials.mathgame.data.state.ConfigState
import eu.tutorials.mathgame.util.FirebaseUtils
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {

    private val _remoteState = MutableStateFlow(ConfigState())
    val remoteState: StateFlow<ConfigState> = _remoteState

    init {
        onEvent(ConfigEvent.FetchColors)
    }

    fun onEvent(event: ConfigEvent) {
        when (event) {
            ConfigEvent.FetchColors -> {
                viewModelScope.launch {
                    try {
                        remoteConfig.fetchAndActivate().await()

                        val colors = FirebaseUtils.getRemoteColors(remoteConfig)

                        _remoteState.value = _remoteState.value.copy(remoteColors = colors)
                    } catch (e: Exception) {
                        _remoteState.value = _remoteState.value.copy(remoteColors = null)
                    }
                }
            }
        }
    }
}
