package domain.usecase.magicItem

import domain.model.magicItem.ItemRarity
import domain.model.magicItem.MagicItem
import domain.repository.MagicItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMagicItemUseCase(private val repository: MagicItemRepository) {

    suspend operator fun invoke(
        name: String,
        rarity: ItemRarity? = null,
    ): Flow<List<MagicItem>> {
        val listOfItems: MutableSet<MagicItem> = mutableSetOf()
        return repository.search(name, rarity).map { items ->
            listOfItems.addAll(items)
            listOfItems.toList()
        }
    }

}