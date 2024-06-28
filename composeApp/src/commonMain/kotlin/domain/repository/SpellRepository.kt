package domain.repository

import data.api.Dnd5Api
import data.database.SqlDatabase
import data.dto.SpellDto
import domain.model.DamageType
import domain.model.Level
import domain.model.spell.MagicSchool
import domain.model.spell.Spell
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.dembeyo.data.SpellDbo
import org.lighthousegames.logging.logging

class SpellRepository(private val spellApi: Dnd5Api, private val dataBase: SqlDatabase) {

    init {
        loadSpellsDatabase()
    }

    private fun loadSpellsDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = spellApi.getSpellByLevelOrSchool()
            result.results.forEach { dto ->
                dataBase.insertSpell(
                    index = dto.index,
                    name = dto.name,
                    level = dto.level.toLong(),
                )
            }
        }
    }

    fun setFavorite(index: String, isFavorite: Boolean) {
        dataBase.updateSpellFavoriteStatus(index, isFavorite)
    }

    private fun SpellDbo.toDomain() = Spell(
        index = id,
        name = name,
        isFavorite = isFavorite == 1L,
        level = Level.fromInt(level.toInt()),
    )

    private fun SpellDto.toDomain(isFavorite: Boolean): Spell {
        val school = MagicSchool.fromIndex(school.index)
            ?: throw IllegalArgumentException("Unknown school index")

        val damageByLevels: Map<Level, Spell.Details.SpellDamage> = damage?.let {
            val values = it.damageAtSlotLevel ?: it.damageAtCharacterLevel
            values?.getDamageByLevel(DamageType.fromIndex(it.damageType.index)) ?: emptyMap()
        } ?: emptyMap()

        return Spell(
            index = index,
            name = name,
            level = Level.fromInt(level),
            isFavorite = isFavorite,
            details = Spell.Details(
                text = desc.joinToString() + higherLevel.joinToString(),
                range = range,
                components = components.joinToString(),
                material = material,
                ritual = ritual,
                duration = duration,
                concentration = concentration,
                castingTime = castingTime,
                attackType = attackType,
                damageByLevel = damageByLevels,
                savingThrow = dc?.let { "${it.dcType.name} saving throw for ${it.dcSuccess} damage" },
                school = school
            )
        )
    }

    fun getListOfSpells(): Flow<List<Spell>> = dataBase.getAllSpells().map {
        it.map { dbo -> dbo.toDomain() }
    }

    private fun Map<Int, String>.getDamageByLevel(damageType: DamageType): Map<Level, Spell.Details.SpellDamage> {
        return this.mapKeys { (level, _) -> Level.fromInt(level) }
            .mapValues { (_, dice) ->
                Spell.Details.SpellDamage(
                    type = damageType,
                    dice = dice
                )
            }
    }

    suspend fun getSpellByIndex(index: String): Spell? {
        try {
            return spellApi.getSpellByIndex(index)?.let { dto ->
                val isFavorite = dataBase.getSpellById(index).firstOrNull()?.isFavorite == 1L
                return dto.toDomain(isFavorite)
            }
        } catch (e: ServerResponseException) {
            Log.e { e.message }
            return null
        }
    }

    companion object {
        val Log = logging("SpellRepository")
    }

}