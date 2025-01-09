package ui.monster.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge

/**
 * State of the search screen.
 *
 * @property searchResult List of monsters to display in the search screen.
 * @property favoriteMonster List of favorite monsters.
 * @property filterChallengeRange Range of challenge to filter the monsters.
 * @property textField Value of the text field in the search screen.
 * @property isLoading Whether the screen is loading or not.
 */
data class SearchMonsterUiState(
    val showFavorites: Boolean = false,
    val searchResult: List<SearchMonsterItem> = emptyList(),
    val favoriteMonster: List<SearchMonsterItem> = emptyList(),
    val filterChallengeRange: ClosedFloatingPointRange<Float> = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat(),
    val textField: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false
) {

    val favoritesByChallenge: Map<Challenge, List<SearchMonsterItem>>
        get() = favoriteMonster
            .sortedBy { it.challenge }
            .groupBy { it.challenge }

    val monsterByChallenge: Map<Challenge, List<SearchMonsterItem>>
        get() = searchResult
            .sortedBy { it.challenge }
            .groupBy { it.challenge }

    val monsterCount: Int
        get() = searchResult.size

    val favoritesCount: Int
        get() = favoriteMonster.size

    val challengeRange = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()

    val minChallenge: Challenge = Challenge.entries[filterChallengeRange.start.toInt()]

    val maxChallenge: Challenge = Challenge.entries[filterChallengeRange.endInclusive.toInt()]

}