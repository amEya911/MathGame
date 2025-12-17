package eu.tutorials.mathgame.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ThemeDto(
    val textBlack: String,
    val textWhite: String,
    val backgroundWhite: String,
    val normalMode: NormalModeDto,
    val difficulty: DifficultyDto,
    val players: PlayersDto,
    val answers: AnswersDto,
    val botMode: BotModeDto,
    val confetti: ConfettiDto
)

@Serializable
data class NormalModeDto(
    val background: String,
    val surface: String,
    val button: String,
    val text: String,
    val topBackground: String
)

@Serializable
data class DifficultyDto(
    val easy: String,
    val medium: String,
    val hard: String
)

@Serializable
data class PlayersDto(
    val playerOne: PlayerDto,
    val playerTwo: PlayerDto
)

@Serializable
data class PlayerDto(
    val primary: String,
    val secondary: String
)

@Serializable
data class AnswersDto(
    val correct: String,
    val wrong: String
)

@Serializable
data class BotModeDto(
    val background: String,
    val box: String
)

@Serializable
data class ConfettiDto(
    val purple: String,
    val blue: String,
    val green: String,
    val red: String,
    val yellow: String
)