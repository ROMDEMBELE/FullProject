package data.database.realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class MagicItemDbo() : RealmObject {
    @PrimaryKey
    var index: String? = null

    var isFavorite: Boolean = false

    var name: String? = null

    var category: CategoryDbo? = null

    var rarity: String? = null

    var description: RealmList<String> = realmListOf()

    var imageUrl: String? = null

    open class CategoryDbo() : EmbeddedRealmObject {
        var index: String? = null

        var name: String? = null

        var url: String? = null
    }
}