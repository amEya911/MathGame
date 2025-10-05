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

    val navigator = { fromRoute: String?, toRoute: String? ->
        if (toRoute == null) {
            navController.popBackStack()
            Unit
        } else if (fromRoute == null) {
            navController.navigate(toRoute)
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Start.route
    ) {
        composable(AppScreen.Start.route) {
            Start(
                startViewModel = startViewModel,
                startState = startState,
                navigator = navigator
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
                gameViewModel.onEvent(GameEvent.InitializeGameModeAndBotLevel(gameMode, botLevel))
            }

            Game(
                gameViewModel = gameViewModel,
                gameState = gameSate,
                navigator = navigator,
                onExitClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class AppScreen(val route: String) {
    data object Start: AppScreen("start")
    data object Game: AppScreen("game")
}

fun interface Navigator {
    fun navigate(fromRoute: String?, toRoute: String?)
}

fun Navigator.navigateTo(toRoute: String, fromRoute: String) {
    this.navigate(fromRoute = fromRoute, toRoute = toRoute)
}

fun Navigator.popBack() {
    this.navigate(null, null)
}

fun Navigator.replaceWith(toRoute: String) {
    this.navigate(fromRoute = null, toRoute = toRoute)
}