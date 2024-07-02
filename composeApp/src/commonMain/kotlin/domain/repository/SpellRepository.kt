package domain.repository

import data.api.Dnd5Api
import data.database.SqlDatabase
import data.dto.SpellDto
import data.dto.SpellDto.SpellDamageDto
import data.dto.SpellDto.SpellDcDto
import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import domain.model.SavingThrow
import domain.model.spell.MagicSchool
import domain.model.spell.Spell
import domain.model.spell.Spell.Details.SpellDamage
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                fetchSpellDatabase()
            } catch (e: Exception) {
                Log.w { "Unable to update the spell database" }
            }
        }
    }

    suspend fun fetchSpellDatabase() {
        spellApi.getSpellByLevelOrSchool().results.forEach { dto ->
            dataBase.insertSpell(
                index = dto.index,
                name = dto.name,
                level = dto.level.toLong(),
            )
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

    private fun SpellDamageDto.toDomain(): Map<Level, SpellDamage> {
        val mapOfDamageByInt = damageAtSlotLevel ?: damageAtCharacterLevel ?: emptyMap()
        val type = damageType?.let { type -> DamageType.fromIndex(type.index) }
        return mapOfDamageByInt
            .mapKeys { (level, _) -> Level.fromInt(level) }
            .mapValues { (_, dice) -> SpellDamage(type = type, dice = dice) }
    }

    private fun SpellDcDto.toDomain() = SavingThrow(
        value = null,
        success = dcSuccess,
        ability = Ability.valueOf(dcType.name)
    )

    private fun SpellDto.toDomain(isFavorite: Boolean): Spell {
        val school = MagicSchool.fromIndex(school.index)
            ?: throw IllegalArgumentException("Unknown school index")

        return Spell(
            index = index,
            name = name,
            level = Level.fromInt(level),
            isFavorite = isFavorite,
            details = Spell.Details(
                description = desc + higherLevel,
                range = range,
                components = components.joinToString(),
                material = material,
                ritual = ritual,
                duration = duration,
                concentration = concentration,
                areaOfEffect = areaOfEffect?.let { "${it.type} ${it.size}" },
                castingTime = castingTime,
                attackType = attackType,
                damageByLevel = damage?.toDomain().orEmpty(),
                savingThrow = dc?.toDomain(),
                school = school
            )
        )
    }

    fun getListOfSpells(): Flow<List<Spell>> = dataBase.getAllSpells().map {
        it.map { dbo -> dbo.toDomain() }
    }

    suspend fun getSpellByIndex(index: String): Spell? {
        return spellApi.getSpellByIndex(index)?.let { dto ->
            val isFavorite = dataBase.getSpellById(index).firstOrNull()?.isFavorite == 1L
            return dto.toDomain(isFavorite)
        }
    }

    companion object {
        val Log = logging("SpellRepository")
    }

}