package eu.tutorials.mathgame.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner

object AppLifecycle {
    @Composable
    fun rememberAppInForeground(): Boolean {
        var isForeground by remember { mutableStateOf(true) }

        DisposableEffect(Unit) {
            val lifecycle = ProcessLifecycleOwner.get().lifecycle
            val observer = LifecycleEventObserver { _, event ->
                isForeground = when (event) {
                    Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME -> true
                    Lifecycle.Event.ON_STOP -> false
                    else -> isForeground
                }
            }

            lifecycle.addObserver(observer)

            onDispose {
                lifecycle.removeObserver(observer)
            }
        }

        return isForeground
    }
}