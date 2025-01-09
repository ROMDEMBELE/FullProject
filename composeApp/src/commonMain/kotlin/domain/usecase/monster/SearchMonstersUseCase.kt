package domain.usecase.monster

import androidx.compose.ui.text.input.TextFieldValue
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMonstersUseCase(
    private val monsterRepository: MonsterRepository,
) {

    suspend operator fun invoke(
        query: TextFieldValue,
        minChallenge: Challenge,
        maxChallenge: Challenge
    ): Flow<Set<Monster>> {
        val listOfMonsters: MutableSet<Monster> = mutableSetOf()
        return monsterRepository.search(
            name = query.text,
            min = minChallenge,
            max = maxChallenge
        ).map { monsters ->
            listOfMonsters.addAll(monsters)
            listOfMonsters
        }
    }
}