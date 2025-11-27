package eu.tutorials.mathgame.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.APP
    ) {
        splashNavGraph(navController)
        appNavGraph(navController)
    }
}

object Graph {
    const val APP = "app"
    const val SPLASH = "splash"
}