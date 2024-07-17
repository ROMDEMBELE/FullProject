package ui.monster.list

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import domain.model.monster.Monster

data class MonsterListUiState(
    val monsterList: List<Monster> = emptyList(),
    val filterChallengeRange: ClosedFloatingPointRange<Float> = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat(),
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
            return monsterList
                .filter { it.challenge.ordinal.toFloat() in filterChallengeRange }
                .filter { it.name.contains(text, true) }
                .sortedBy { it.challenge }
                .groupBy { it.challenge }
        }

    val monsterCount: Int
        get() = monsterByChallenge.flatMap { it.value }.size

    val hasError get() = error != null

    val challengeRange = Challenge.CR_0.ordinal.toFloat()..Challenge.CR_30.ordinal.toFloat()

    val minChallenge: Challenge = Challenge.entries[filterChallengeRange.start.toInt()]

    val maxChallenge: Challenge = Challenge.entries[filterChallengeRange.endInclusive.toInt()]

    val favoriteCounter get() = favoriteMonsterByChallenge.values.sumOf { it.size }
}