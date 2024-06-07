package ui.monster

import androidx.compose.ui.text.input.TextFieldValue
import domain.Challenge
import domain.Monster

data class MonsterListUiState(
    val monsterByChallenge: Map<Challenge, List<Monster>> = emptyMap(),
    val minChallenge: Challenge = Challenge.CR_0,
    val maxChallenge: Challenge = Challenge.CR_30,
    var textField: TextFieldValue = TextFieldValue()
)