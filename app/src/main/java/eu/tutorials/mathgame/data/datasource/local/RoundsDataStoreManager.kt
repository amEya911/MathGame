package eu.tutorials.mathgame.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "rounds_prefs")

@Singleton
class RoundsDataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val LEVEL_SLIDER_KEY = floatPreferencesKey("level_slider_position")
    }

    val levelSliderPositionFlow: Flow<Float> = context.dataStore.data.map { prefs ->
        prefs[LEVEL_SLIDER_KEY] ?: 0f
    }

    suspend fun saveLevelSliderPosition(position: Float) {
        context.dataStore.edit { prefs ->
            prefs[LEVEL_SLIDER_KEY] = position
        }
    }
}