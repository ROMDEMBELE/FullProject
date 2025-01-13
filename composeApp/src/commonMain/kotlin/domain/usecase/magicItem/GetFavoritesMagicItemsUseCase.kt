package domain.usecase.magicItem

import domain.model.magicItem.MagicItem
import domain.repository.MagicItemRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesMagicItemsUseCase(private val repository: MagicItemRepository) {

    suspend operator fun invoke(): Flow<List<MagicItem>> {
        return repository.getFavorites()
    }

}