package eu.tutorials.mathgame

import androidx.compose.ui.test.junit4.createComposeRule
import eu.tutorials.mathgame.navigation.AppNavGraph
import org.junit.Rule
import org.junit.Test

class MathGameTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun enter() {
        rule.setContent { AppNavGraph(remoteColors = null) }
    }
}