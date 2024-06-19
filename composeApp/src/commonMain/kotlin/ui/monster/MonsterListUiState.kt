package ui.monster

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import domain.model.monster.Monster

data class MonsterListUiState(
    val monsterByChallenge: Map<Challenge, List<Monster>> = emptyMap(),
    val minChallenge: Challenge = Challenge.CR_0,
    val maxChallenge: Challenge = Challenge.CR_30,
    var textField: TextFieldValue = TextFieldValue(),
    val favoriteMonsterByChallenge: Map<Challenge, List<Monster>> = emptyMap()
) {
    val favoriteCounter get() = favoriteMonsterByChallenge.values.sumOf { it.size }
}