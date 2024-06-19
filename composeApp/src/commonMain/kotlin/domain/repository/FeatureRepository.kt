package domain.repository

import data.api.DndApi
import domain.model.Level
import domain.model.character.Feature
import io.ktor.client.plugins.ServerResponseException
import org.lighthousegames.logging.logging

class FeatureRepository(private val dndApi: DndApi) {

    suspend fun getAllFeatures(): List<Feature> {
        try {
            val result = dndApi.getFeatures()
            return result.results.map {
                Feature(
                    index = it.index, name = it.name
                )
            }

        } catch (e: ServerResponseException) {
            Log.e { e.message }
            return emptyList()
        }
    }

    suspend fun getByIndex(index: String): Feature? {
        try {
            return dndApi.getFeature(index)?.let { dto ->
                return Feature(
                    index = dto.index,
                    name = dto.name,
                    classIndex = dto.classInfo?.index,
                    level = dto.level?.let { Level.fromInt(it) },
                    desc = dto.desc?.joinToString()
                )
            }
        } catch (e: ServerResponseException) {
            Log.e { e.message }
            return null
        }
    }

    companion object {
        val Log = logging("FeatureRepository")
    }

}