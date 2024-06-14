package repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.Level
import domain.spell.MagicSchool
import domain.spell.Spell
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class SpellRepository(private val spellApi: DndApi, private val dataBase: SqlDatabase) {

    init {
        loadSpellsDatabase()
    }

    private fun loadSpellsDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = spellApi.getSpellByLevelOrSchool()
            result.results.forEach { dto ->
                dataBase.createSpell(
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

    fun getSpells(): Flow<List<Spell>> {
        return dataBase.getAllSpells().map {
            it.map { dbo ->
                Spell(
                    index = dbo.id,
                    name = dbo.name,
                    isFavorite = dbo.isFavorite == 1L,
                    level = Level.fromInt(dbo.level.toInt()),
                )
            }
        }
    }

    suspend fun getSpellByIndex(index: String): Spell.SpellDetails? {
        try {
            return spellApi.getSpellByIndex(index)?.let { dto ->

                val damageSlot = when {
                    !dto.damage.damageAtCharacterLevel.isNullOrEmpty() -> dto.damage.damageAtCharacterLevel
                    !dto.damage.damageAtSlotLevel.isNullOrEmpty() -> dto.damage.damageAtSlotLevel
                    else -> emptyMap()
                }.mapKeys { (key, _) -> Level.fromInt(key) }

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
                    damageType = dto.damage.damageType.name,
                    damageSlot = damageSlot,
                    save = dto.dc.let { "${it.dcType.name}(${it.dcSuccess})" },
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