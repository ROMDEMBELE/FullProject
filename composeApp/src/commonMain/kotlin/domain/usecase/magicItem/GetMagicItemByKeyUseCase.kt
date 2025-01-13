package domain.usecase.magicItem

import domain.model.magicItem.MagicItem
import domain.repository.MagicItemRepository

class GetMagicItemByKeyUseCase(private val repository: MagicItemRepository) {

    suspend operator fun invoke(key: String): MagicItem {
        return repository.getByKey(key)
    }
}