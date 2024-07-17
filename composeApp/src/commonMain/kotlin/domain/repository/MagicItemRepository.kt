package domain.repository

import data.api.Dnd5Api
import data.database.realm.MagicItemDbo
import data.database.realm.RealmDataBase
import domain.model.magicItem.MagicItem
import domain.model.magicItem.Rarity
import io.realm.kotlin.ext.toRealmList
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
            // check if item is already in database
            if (database.getMagicItemById(referenceDto.index) == null) {
                val dto = api.getMagicItemByIndex(referenceDto.index)
                database.insertOrReplaceMagicItem(
                    MagicItemDbo().apply {
                        index = dto.index
                        name = dto.name
                        isFavorite = false
                        rarity = dto.rarity.name
                        description = dto.desc.toRealmList()
                        category = MagicItemDbo.CategoryDbo().apply {
                            index = dto.category.index
                            name = dto.category.name
                            url = dto.category.url
                        }
                    }
                )
            }
        }
        Log.i { "fetchData ${result.size} items registered in database" }
    }

    suspend fun setFavorite(index: String, isFavorite: Boolean) {
        database.updateMagicItemFavoriteStatus(index, isFavorite)
    }

    fun getAll(): Flow<List<MagicItem>> =
        database.getAllMagicItems().map { it.map { dbo -> dbo.toDomain() } }

    fun getByIndex(index: String): MagicItem? =
        database.getMagicItemById(index)?.toDomain()

    private fun MagicItemDbo.toDomain() = MagicItem(
        index = index.toString(),
        isFavorite = isFavorite,
        name = name.toString(),
        category = category?.toDomain() ?: throw IllegalStateException("Category is null"),
        rarity = Rarity.fromText(rarity.toString()),
        description = description.toList(),
        imageUrl = null
    )

    private fun MagicItemDbo.CategoryDbo.toDomain() = MagicItem.Category(
        index = index.toString(),
        name = name.toString(),
        url = url.toString()
    )

    companion object {
        val Log = logging("MagicItemRepository")
    }
}