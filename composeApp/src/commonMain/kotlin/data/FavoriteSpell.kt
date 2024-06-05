package data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class FavoriteSpell() : RealmObject {

    @PrimaryKey
    var index: String = ""

    constructor(index: String) : this() {
        this.index = index
    }

}