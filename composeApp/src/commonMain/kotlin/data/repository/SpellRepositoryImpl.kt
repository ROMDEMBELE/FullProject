package data.repository

import common.safeGetBoolean
import common.safeGetInt
import common.safeGetJsonArray
import common.safeGetString
import data.api.SpellApi
import data.api.dto.SearchResultDto
import data.database.sqlDelight.SqlDatabase
import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import domain.model.spell.MagicSchool
import domain.model.spell.Spell
import domain.repository.SpellRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.dembeyo.data.SpellDbo
import org.koin.core.error.MissingPropertyException

class SpellRepositoryImpl(
    private val spellApi: SpellApi, private val database: SqlDatabase
) : SpellRepository {

    private fun SpellDbo.toSpell() = Spell(
        isFavorite = true,
        key = this.key,
        name = this.name,
        level = this.level,
        description = this.description,
        higherLevel = this.higher_level,
        school = this.school,
        range = this.range,
        verbal = this.verbal,
        somatic = this.somatic,
        ritual = this.ritual,
        duration = this.duration,
        castingTime = this.casting_time,
        targetCount = this.target_count?.toInt(),
        concentration = this.concentration,
        attackRoll = this.attack_roll,

        cost = this.cost,
        savingThrowAbility = this.saving_throw_ability,
        castingOptions = this.casting_options.orEmpty()
    )

    @Throws(MissingPropertyException::class)
    private fun JsonObject.toSpellOption(defaultLevel: Level) = Spell.SpellOption(
        level = this["type"]?.jsonPrimitive?.contentOrNull?.let {
            when (it) {
                "slot_level_0" -> Level.LEVEL_0
                "slot_level_1" -> Level.LEVEL_1
                "slot_level_2" -> Level.LEVEL_2
                "slot_level_3" -> Level.LEVEL_3
                "slot_level_4" -> Level.LEVEL_4
                "slot_level_5" -> Level.LEVEL_5
                "slot_level_6" -> Level.LEVEL_6
                "slot_level_7" -> Level.LEVEL_7
                "slot_level_8" -> Level.LEVEL_8
                "slot_level_9" -> Level.LEVEL_9
                else -> defaultLevel
            }
        } ?: throw MissingPropertyException("Unable to parse level"),
        targetCount = this["target_count"]?.jsonPrimitive?.intOrNull,
        damageRoll = this["damage_roll"]?.jsonPrimitive?.contentOrNull,
        duration = this["duration"]?.jsonPrimitive?.contentOrNull,
    )

    private fun JsonObject.toSpell(isFavorite: Boolean): Spell? {
        try {

            val level = this.safeGetInt("level").let { Level.fromInt(it) }
            return Spell(isFavorite = isFavorite,
                key = this.safeGetString("key"),
                name = this.safeGetString("name"),
                level = level,
                description = this.safeGetString("desc"),
                higherLevel = this["higher_level"]?.jsonPrimitive?.contentOrNull,
                school = this.safeGetString("school").let { MagicSchool.fromUrl(it) },
                range = this.safeGetString("range_text"),
                verbal = this.safeGetBoolean("verbal"),
                somatic = this.safeGetBoolean("somatic"),
                ritual = this.safeGetBoolean("ritual"),
                duration = this.safeGetString("duration"),
                castingTime = this.safeGetString("casting_time"),
                targetCount = this.safeGetInt("target_count"),
                concentration = this.safeGetBoolean("concentration"),
                attackRoll = this.safeGetBoolean("attack_roll"),
                damageType = this.safeGetJsonArray("damage_types")
                    .map { DamageType.fromString(it.jsonPrimitive.content) },
                cost = this["cost"]?.jsonPrimitive?.contentOrNull,
                savingThrowAbility = this["saving_throw_ability"]?.jsonPrimitive?.contentOrNull?.let {
                    if (it.isBlank()) null else Ability.fromFullName(it)
                },
                castingOptions = this.safeGetJsonArray("casting_options").map {
                    it.jsonObject.toSpellOption(level)
                })
        } catch (e: MissingPropertyException) {
            Napier.w { "Unable to parse spell: ${e.message}" }
            return null
        }
    }


    private fun fetchSpells(call: suspend () -> SearchResultDto<JsonObject>): Flow<List<Spell>> {
        return flow {
            val favorites = database.getAllSpells().firstOrNull().orEmpty()
            var searchResult = call()
            do {
                val monsters = searchResult.results.mapNotNull { jsonObject ->
                    val isFavorite = favorites.any { it.key == jsonObject.safeGetString("key") }
                    jsonObject.toSpell(isFavorite)
                }
                emit(monsters)
                searchResult.next?.let { next ->
                    searchResult = spellApi.getNextPage(next)
                }
            } while (searchResult.next != null)
        }
    }

    override suspend fun getByKey(key: String): Spell {
        return database.getSpellById(key)?.toSpell() ?: run {
            spellApi.findSpell(key).results.firstOrNull()?.toSpell(false)
                ?: throw Exception("Unable to find spell with key $key")
        }

    }

    override suspend fun search(
        name: String, min: Level, max: Level
    ): Flow<List<Spell>> {
        return fetchSpells { spellApi.search(name, min.level, max.level) }.map { list ->
            list.distinctBy { it.key }.distinctBy { it.name }
        }
    }

    override suspend fun addFavorite(spell: Spell) {
        database.insertOrUpdateSpell(spell)
    }

    override suspend fun removeFavorite(key: String) {
        database.deleteSpellById(key)
    }

    override suspend fun getFavorites(): Flow<List<Spell>> {
        return database.getAllSpells().map { list -> list.map { it.toSpell() } }
    }

    override suspend fun getFavorite(key: String): Spell? {
        return database.getSpellById(key)?.toSpell()
    }
}