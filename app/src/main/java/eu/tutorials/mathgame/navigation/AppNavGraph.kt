package eu.tutorials.mathgame.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.tutorials.mathgame.data.event.GameEvent
import eu.tutorials.mathgame.data.model.BotLevel
import eu.tutorials.mathgame.data.model.GameMode
import eu.tutorials.mathgame.data.model.RemoteColors
import eu.tutorials.mathgame.ui.screen.Game
import eu.tutorials.mathgame.ui.screen.Start
import eu.tutorials.mathgame.ui.viewmodel.GameViewModel
import eu.tutorials.mathgame.ui.viewmodel.StartViewModel

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val startViewModel: StartViewModel = hiltViewModel()
    val startState by startViewModel.startState.collectAsState()

    val gameViewModel: GameViewModel = hiltViewModel()
    val gameSate by gameViewModel.gameState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.route
    ) {
        composable(AppScreen.Start.route) {
            Start(
                startViewModel = startViewModel,
                startState = startState,
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

            LaunchedEffect(Unit) {
                gameViewModel.onEvent(GameEvent.StartCountDownAndNextQuestion)
            }

            Game(
                gameViewModel = gameViewModel,
                gameState = gameSate,
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