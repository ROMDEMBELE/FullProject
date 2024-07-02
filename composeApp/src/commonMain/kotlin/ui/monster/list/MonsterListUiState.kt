package ui.monster.list

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import domain.model.monster.Monster

data class MonsterListUiState(
    val monsterList: List<Monster> = emptyList(),
    val minChallenge: Challenge = Challenge.CR_0,
    val maxChallenge: Challenge = Challenge.CR_30,
    var textField: TextFieldValue = TextFieldValue(),
    val error: String? = null,
    val isReady: Boolean = false
) {

    val favoriteMonsterByChallenge: Map<Challenge, List<Monster>>
        get() = monsterList.filter { it.isFavorite }.sortedBy { it.challenge }
            .groupBy { it.challenge }

    val monsterByChallenge: Map<Challenge, List<Monster>>
        get() {
            val text = textField.text
            val challengeRange = minChallenge.rating..maxChallenge.rating
            return monsterList
                .filter { it.name.contains(text, true) && it.challenge.rating in challengeRange }
                .sortedBy { it.challenge }
                .groupBy { it.challenge }
        }

    val hasError get() = error != null

    val favoriteCounter get() = favoriteMonsterByChallenge.values.sumOf { it.size }
}