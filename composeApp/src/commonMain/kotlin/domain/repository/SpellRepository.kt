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

    fun setSpellIsFavorite(index: String, isFavorite: Boolean) {
        dataBase.updateSpellFavoriteStatus(index, isFavorite)
    }

    private fun SpellDbo.toDomain() = Spell(
        index = id,
        name = name,
        isFavorite = isFavorite == 1L,
        level = Level.fromInt(level.toInt()),
    )

    fun getListOfSpells(): Flow<List<Spell>> = dataBase.getAllSpells().map {
        it.map { dbo -> dbo.toDomain() }
    }

    private fun SpellDto.getDamageByLevel(): Map<Level, Spell.SpellDamage> {
        val damageLevels = damage?.damageAtSlotLevel ?: damage?.damageAtCharacterLevel
        return damageLevels?.let { levels ->
            levels.mapKeys { (level, _) -> Level.fromInt(level) }
                .mapValues { (_, dice) ->
                    Spell.SpellDamage(
                        type = DamageType.fromIndex(damage?.damageType?.index.toString()),
                        dice = dice
                    )
                }
        } ?: emptyMap()
    }

    suspend fun getSpellByIndex(index: String): Spell.SpellDetails? {
        try {
            return spellApi.getSpellByIndex(index)?.let { dto ->
                val isFavorite: Boolean =
                    dataBase.getSpellById(index).firstOrNull()?.isFavorite == 1L

                val school = MagicSchool.fromIndex(dto.school.index)
                    ?: throw IllegalArgumentException("Unknown school index")

                Spell.SpellDetails(
                    index = dto.index,
                    name = dto.name,
                    level = Level.fromInt(dto.level),
                    isFavorite = isFavorite,
                    text = dto.desc.joinToString() + dto.higherLevel.joinToString(),
                    range = dto.range,
                    components = dto.components.joinToString(),
                    material = dto.material,
                    ritual = dto.ritual,
                    duration = dto.duration,
                    concentration = dto.concentration,
                    castingTime = dto.castingTime,
                    attackType = dto.attackType,
                    damageByLevel = dto.getDamageByLevel(),
                    savingThrow = dto.dc?.let { "${it.dcType.name} saving throw for ${it.dcSuccess} damage" },
                    school = school
                )
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