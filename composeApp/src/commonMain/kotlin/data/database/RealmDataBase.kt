package data.database

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmDataBase {

    private val config = RealmConfiguration.create(
        schema = setOf()
    )

    private val realm: Realm = Realm.open(config)

}