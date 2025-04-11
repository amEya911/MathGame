package eu.tutorials.mathgame.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.ui.screen.Game
import eu.tutorials.mathgame.ui.screen.Start

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.route
    ) {
        composable(AppScreen.Start.route) {
            Start(
                onStartClicked = {gameMode, botLevel ->
                    navController.navigate("${AppScreen.Game.route}/$gameMode?botLevel=$botLevel")
                }
            )
        }

        composable(
            route = "${AppScreen.Game.route}/{gameMode}?botLevel={botLevel}",
            arguments = listOf(
                navArgument("gameMode") { type = NavType.StringType },
                navArgument("botLevel") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val gameModeArg = backStackEntry.arguments?.getString("gameMode")
            val botLevelArg = backStackEntry.arguments?.getString("botLevel")

            val gameMode = GameMode.valueOf(gameModeArg ?: GameMode.NORMAL.name)
            val botLevel = botLevelArg?.takeIf { it.isNotBlank() }?.let { BotLevel.valueOf(it) }

            Game(
                onExitClicked = {
                    navController.popBackStack()
                },
                gameMode = gameMode,
                botLevel = botLevel
            )
        }
    }
}

sealed class AppScreen(val route: String) {
    data object Start: AppScreen("start")
    data object Game: AppScreen("game")
}