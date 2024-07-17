package ui.magicItem.details

import domain.model.magicItem.MagicItem


data class MagicItemDetailsUiState(
    val magicItem: MagicItem? = null,
    val isReady: Boolean = false,
)