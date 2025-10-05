package eu.tutorials.mathgame.data.event

sealed interface ConfigEvent {
    data object FetchColors: ConfigEvent
}