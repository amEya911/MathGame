package eu.tutorials.mathgame.data.model

import androidx.compose.ui.graphics.Color

data class PlayerSectionSpec(
    val isBlue: Boolean,
    val rotation: Float,
    val color: Color,
    val borderColor: Color
)