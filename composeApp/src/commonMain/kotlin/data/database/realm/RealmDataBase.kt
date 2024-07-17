package data.database.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmDataBase {

    private val config = RealmConfiguration.create(
        schema = setOf(MagicItemDbo::class, MagicItemDbo.CategoryDbo::class)
    )

    private val realm: Realm = Realm.open(config)

    suspend fun insertOrReplaceMagicItem(magicItemDbo: MagicItemDbo
    ) {
        realm.write {
            copyToRealm(updatePolicy = UpdatePolicy.ALL, instance = magicItemDbo)
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