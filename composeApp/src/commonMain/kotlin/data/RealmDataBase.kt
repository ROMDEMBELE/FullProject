package data

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class RealmDataBase {

    private val config = RealmConfiguration.create(
        schema = setOf(
            FavoriteSpell::class
        )
    )
    private val realm: Realm = Realm.open(config)

    suspend fun saveFavoriteSpell(spell: FavoriteSpell) {
        realm.write { copyToRealm(spell) }
    }

    fun getFavoriteSpells(): List<FavoriteSpell> {
        return realm.query(FavoriteSpell::class).find()
    }

    fun findFavoriteSpell(index: String): FavoriteSpell? {
        return realm.query(FavoriteSpell::class, "index == \"$index\"").first().find()
    }

    suspend fun deleteFavoriteSpell(index: String) {
        realm.query<FavoriteSpell>("index == \"$index\"").first().find()?.let { favorite ->
            realm.write {
                findLatest(favorite)?.let { delete(it) }
            }
        }
    }

}