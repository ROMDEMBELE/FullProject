package data.repository

import common.safeGetBoolean
import common.safeGetDouble
import common.safeGetString
import data.api.ItemApi
import data.api.dto.SearchResultDto
import data.database.sqlDelight.SqlDatabase
import domain.model.magicItem.ItemCategory
import domain.model.magicItem.ItemRarity
import domain.model.magicItem.MagicItem
import domain.repository.MagicItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.dembeyo.data.MagicItemDbo

class MagicItemRepositoryImpl(private val api: ItemApi, private val database: SqlDatabase) :
    MagicItemRepository {

    private val temporaryRemoteMagicItems = mutableListOf<MagicItem>()

    private fun JsonObject.toMagicItem(isFavorite: Boolean) = MagicItem(
        isFavorite = isFavorite,
        key = this.safeGetString("key"),
        name = this.safeGetString("name"),
        description = this.safeGetString("desc"),
        cost = this["cost"]?.jsonPrimitive?.doubleOrNull,
        weight = this.safeGetDouble("weight"),
        isMagical = this.safeGetBoolean("is_magic_item"),
        category = ItemCategory.fromUrl(this.safeGetString("category")),
        rarity = this["rarity"]?.jsonPrimitive?.contentOrNull.let { ItemRarity.fromUrl(it) },
        requireAttunement = this.safeGetBoolean("requires_attunement")
    )

    private fun MagicItemDbo.toMagicItem() = MagicItem(
        key = key,
        isFavorite = true,
        isMagical = is_magical,
        name = name,
        description = description,
        cost = cost,
        weight = weight,
        requireAttunement = require_attunement,
        category = category,
        rarity = rarity
    )

    private fun fetchItems(call: suspend () -> SearchResultDto<JsonObject>): Flow<List<MagicItem>> =
        flow {
            val favorites = database.getAllMagicItem().firstOrNull().orEmpty()
            var searchResult = call()
            do {
                // Convert search result to monsters list
                val magicItems = searchResult.results.map { jsonObject ->
                    val isFavorite = favorites.any { it.key == jsonObject.safeGetString("key") }
                    jsonObject.toMagicItem(isFavorite)
                }
                // Save monsters in memory temporarily
                temporaryRemoteMagicItems.addAll(magicItems)
                // Emit monsters
                emit(magicItems)
                // Fetch next page of result
                searchResult.next?.let { next ->
                    searchResult = api.getNextPage(next)
                }
            } while (searchResult.next != null)
        }

    override suspend fun addFavorite(magicItem: MagicItem) {
        database.insertOrUpdateMagicItem(magicItem)
    }

    override suspend fun removeFavorite(key: String) {
        database.deleteMagicItemById(key)
    }

    override suspend fun getFavorites(): Flow<List<MagicItem>> {
        return database.getAllMagicItem().map { list -> list.map { it.toMagicItem() } }
    }

    override suspend fun getFavorite(key: String): MagicItem? {
        return database.getMagicItemById(key)?.toMagicItem()
    }

    override suspend fun getByKey(key: String): MagicItem {
        // Look for item in database
        return database.getMagicItemById(key)?.toMagicItem() ?: run {
            temporaryRemoteMagicItems.firstOrNull { it.key == key }
        } ?: run {
            val searchResult = api.getByKey(key)
            if (searchResult.results.isEmpty()) {
                throw NoSuchElementException("No item found with key $key")
            }
            return searchResult.results.first().toMagicItem(false)
        }
    }

    override suspend fun search(
        name: String,
        rarity: ItemRarity?,
    ): Flow<List<MagicItem>> {
        return fetchItems { api.search(name, rarity?.key) }.map { list ->
            list.distinctBy { it.key }.distinctBy { it.name }
        }
    }


}