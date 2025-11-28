package eu.tutorials.mathgame.data.datasource.remote

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsLogger @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun log(event: LogEvents, params: Bundle.() -> Unit = {}) {
        val bundle = Bundle().apply(params)
        firebaseAnalytics.logEvent(event.name, bundle)
    }
}