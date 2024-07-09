package domain.usecase.common

import domain.model.magicItem.MagicItem
import domain.model.monster.Monster
import domain.model.spell.Spell
import domain.repository.MagicItemRepository
import domain.repository.MonsterRepository
import domain.repository.SpellRepository

class RemoveFromFavoriteUseCase(
    private val spellRepository: SpellRepository,
    private val monsterRepository: MonsterRepository,
    private val magicItemRepository: MagicItemRepository
) {

    suspend operator fun invoke(item: Any) {
        when (item) {
            is Spell -> spellRepository.setFavorite(item.index, false)
            is Monster -> monsterRepository.setFavorite(item.index, false)
            is MagicItem -> magicItemRepository.setFavorite(item.index, false)
            else -> throw IllegalArgumentException("Type ${item::class} is not valid")
        }
    }
}