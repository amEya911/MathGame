package eu.tutorials.mathgame.util

import eu.tutorials.mathgame.data.model.Winner
import eu.tutorials.mathgame.data.state.GameState

object WinnerUtils {
    fun getWinner(maxWinningPoints: Long?, gameState: GameState): Winner? {
        if (maxWinningPoints == null || maxWinningPoints == 0L) return null

        val target = maxWinningPoints.toInt()

        return when {
            gameState.blueScore == target -> Winner.BLUE
            gameState.redScore == target -> Winner.RED
            else -> null
        }
    }
}