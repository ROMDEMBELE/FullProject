package domain.repository

import data.api.Dnd5Api
import data.database.realm.MagicItemDbo
import data.database.realm.RealmDataBase
import domain.model.magicItem.MagicItem
import domain.model.magicItem.Rarity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class MagicItemRepository(private val api: Dnd5Api, private val database: RealmDataBase) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        val result = api.getMagicItems().results
        result.forEach { referenceDto ->
            val dto = api.getMagicItemByIndex(referenceDto.index)
            database.insertOrReplaceMagicItem(
                index = dto.index,
                name = dto.name,
                isFavorite = false,
                rarity = Rarity.fromText(dto.rarity.name),
                description = dto.desc,
                categoryIndex = dto.category.index,
                categoryName = dto.category.name,
                categoryUrl = dto.category.url,
            )
        }
        Log.i { "fetchData ${result.size} items registered in database" }
    }

    suspend fun setFavorite(index: String, isFavorite: Boolean) {
        database.updateMagicItemFavoriteStatus(index, isFavorite)
    }

    fun getAll(): Flow<List<MagicItem>> =
        database.getAllMagicItems().map { it.map { dbo -> dbo.toDomain() } }

    private fun MagicItemDbo.toDomain() = MagicItem(
        index = index.toString(),
        isFavorite = isFavorite,
        name = name.toString(),
        category = category?.name.toString(),
        rarity = Rarity.valueOf(rarity.toString()),
        description = description
    )

    companion object {
        val Log = logging("MagicItemRepository")
    }
}