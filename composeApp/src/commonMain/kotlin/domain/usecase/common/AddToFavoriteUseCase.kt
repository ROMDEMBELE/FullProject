package domain.usecase.common

import domain.model.magicItem.MagicItem
import domain.model.monster.Monster
import domain.model.spell.Spell
import domain.repository.MagicItemRepository
import domain.repository.MonsterRepository
import domain.repository.SpellRepository

class AddToFavoriteUseCase(
    private val spellRepository: SpellRepository,
    private val monsterRepository: MonsterRepository,
    private val magicItemRepository: MagicItemRepository
) {

    suspend operator fun invoke(item: Any) {
        when (item) {
            is Spell -> spellRepository.setFavorite(item.index, true)
            is Monster -> monsterRepository.setFavorite(item.index, true)
            is MagicItem -> magicItemRepository.setFavorite(item.index, true)
            else -> throw IllegalArgumentException("Type ${item::class} is not valid")
        }
    }
}