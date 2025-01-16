package domain.usecase.monster

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.repository.MonsterRepository
import domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMonstersUseCase(
    private val monsterRepository: MonsterRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(
        query: TextFieldValue,
        minChallenge: Challenge,
        maxChallenge: Challenge
    ): Flow<List<Monster>> {
        // Save the search criteria in the settings
        val range = minChallenge.ordinal.toFloat()..maxChallenge.ordinal.toFloat()
        settingsRepository.saveRange(SettingsRepository.SEARCH_MONSTER_CHALLENGE_RANGE, range)

        val listOfMonsters: MutableSet<Monster> = mutableSetOf()

        return monsterRepository.search(
            name = query.text,
            min = minChallenge,
            max = maxChallenge
        ).map { monsters ->
            listOfMonsters.addAll(monsters)
            listOfMonsters.toList().distinctBy { it.name }
        }
    }
}