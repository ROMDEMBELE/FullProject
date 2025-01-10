package data.repository

import common.safeGetBoolean
import common.safeGetJsonArray
import common.safeGetString
import data.api.FeatApi
import data.database.sqlDelight.SqlDatabase
import domain.model.Feat
import domain.repository.FeatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.dembeyo.data.FeatDbo

class FeatRepositoryImpl(private val api: FeatApi, private val database: SqlDatabase) :
    FeatRepository {

    init {
        fetchData()
    }

    private fun FeatDbo.toFeat() = Feat(
        key = this.key,
        hasPrerequisites = this.hasPrerequisite,
        prerequisite = this.prerequisite,
        name = this.name,
        benefits = this.benefits
    )

    private fun JsonObject.toFeat() = Feat(
        key = this.safeGetString("key"),
        hasPrerequisites = this.safeGetBoolean("has_prerequisite"),
        prerequisite = this["prerequisite"]?.jsonPrimitive?.contentOrNull,
        name = this.safeGetString("name"),
        benefits = this.safeGetJsonArray("benefits").map {
            it.jsonObject.safeGetString("desc")
        }
    )

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            var searchResult = api.fetchAll()
            do {
                searchResult.results.map { it.toFeat() }.forEach {
                    database.insertOrUpdateFeat(it)
                }
                searchResult.next?.let {
                    searchResult = api.fetchAll()
                }

            } while (searchResult.next != null)
        }

    }

    override fun getAllFeat(): Flow<List<Feat>> {
        return database.getAllFeat().map { list -> list.map { it.toFeat() } }
    }
}