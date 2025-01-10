package ui.monster.search

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * State of the search screen.
 *
 * @property searchResult List of monsters to display in the search screen.
 * @property favorites List of favorite monsters.
 * @property filterChallengeRange Range of challenge to filter the monsters.
 * @property searchTextField Value of the text field in the search screen.
 * @property isLoading Whether the screen is loading or not.
 */
@Serializable
data class SearchMonsterUiState(
    val searchResult: List<SearchMonsterItem> = emptyList(),
    val favorites: List<SearchMonsterItem> = emptyList(),
    val filterChallengeRange: ClosedFloatingPointRange<Float> = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat(),
    @Contextual
    val searchTextField: TextFieldValue = TextFieldValue(),
    val isLoading: Boolean = false
) {

    val favoritesByChallenge: Map<Challenge, List<SearchMonsterItem>>
        get() = favorites
            .sortedBy { it.challenge }
            .groupBy { it.challenge }

    val monsterByChallenge: Map<Challenge, List<SearchMonsterItem>>
        get() = searchResult
            .sortedBy { it.challenge }
            .groupBy { it.challenge }

    val searchResultCount: Int
        get() = searchResult.size

    val favoritesCount: Int
        get() = favorites.size

    val challengeRange = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()

    val minChallenge: Challenge = Challenge.entries[filterChallengeRange.start.toInt()]

    val maxChallenge: Challenge = Challenge.entries[filterChallengeRange.endInclusive.toInt()]

}