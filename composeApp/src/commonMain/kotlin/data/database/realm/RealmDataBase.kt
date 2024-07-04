package data.database.realm

import domain.model.magicItem.Rarity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmDataBase {

    private val config = RealmConfiguration.create(
        schema = setOf(MagicItemDbo::class, MagicItemDbo.CategoryDbo::class)
    )

    private val realm: Realm = Realm.open(config)

    suspend fun insertOrReplaceMagicItem(
        index: String,
        name: String,
        rarity: Rarity,
        description: List<String>,
        isFavorite: Boolean,
        categoryIndex: String,
        categoryName: String,
        categoryUrl: String,
    ) {
        realm.write {
            copyToRealm(updatePolicy = UpdatePolicy.ALL, instance = MagicItemDbo().apply {
                this.index = index
                this.isFavorite = isFavorite
                this.name = name
                this.category = MagicItemDbo.CategoryDbo().apply {
                    this.index = categoryIndex
                    this.name = categoryName
                    this.url = categoryUrl
                }
                this.rarity = rarity.name
                this.description = description.toRealmList()
            })
        }
    }

    suspend fun updateMagicItemFavoriteStatus(index: String, boolean: Boolean) {
        realm.write {
            query<MagicItemDbo>("index == $0", index).first().find()?.let { magicItem ->
                copyToRealm(magicItem.apply { isFavorite = boolean })
            }
        }
    }

    fun getMagicItemById(id: String): MagicItemDbo? {
        return realm.query<MagicItemDbo>("index == $0", id).find().firstOrNull()
    }

    fun getAllMagicItems(): Flow<List<MagicItemDbo>> {
        return realm.query<MagicItemDbo>().asFlow().map { change: ResultsChange<MagicItemDbo> ->
            change.list
        }
    }
}