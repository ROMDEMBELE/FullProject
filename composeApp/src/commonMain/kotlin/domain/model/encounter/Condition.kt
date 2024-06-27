package domain.model.encounter

import androidx.compose.ui.graphics.Color

data class Condition(
    val index: String,
    val name: String,
    val color: Color,
    val description: List<String>
)