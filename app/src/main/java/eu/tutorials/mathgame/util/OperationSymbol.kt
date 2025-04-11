package eu.tutorials.mathgame.util

object OperationSymbol {

    fun getOperationSymbol(operation: Int): String {
        return when (operation) {
            1 -> "+"
            2 -> "-"
            3 -> "×"
            4 -> "÷"
            else -> "?"
        }
    }
}