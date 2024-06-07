package repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.Level
import domain.MagicSchool
import domain.Spell
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

    suspend fun getSpellByIndex(index: String): Spell? {
        try {
            return spellApi.getSpellByIndex(index)?.let { dto ->
                var fullDesc = dto.desc.orEmpty().joinToString()
                fullDesc += dto.higher_level.orEmpty().joinToString()
                val damageSlot: Map<Level, String> =
                    if (dto.damage?.damage_at_character_level.orEmpty().isNotEmpty()) {
                        dto.damage?.damage_at_character_level.orEmpty()
                            .mapKeys { (key, _) -> Level.fromInt(key) }
                    } else if (dto.damage?.damage_at_slot_level.orEmpty().isNotEmpty()) {
                        dto.damage?.damage_at_slot_level.orEmpty()
                            .mapKeys { (key, _) -> Level.fromInt(key) }
                    } else emptyMap()

                val isFavorite: Boolean = dataBase.getSpellById(index).firstOrNull()?.isFavorite == 1L
                Spell(
                    index = dto.index,
                    name = dto.name,
                    level = Level.fromInt(dto.level),
                    isFavorite = isFavorite,
                    text = fullDesc,
                    range = dto.range,
                    components = dto.components.orEmpty().joinToString(),
                    material = dto.material,
                    ritual = dto.ritual,
                    duration = dto.duration,
                    concentration = dto.concentration,
                    casting_time = dto.casting_time,
                    attack_type = dto.attack_type,
                    damageType = dto.damage?.damage_type?.name,
                    damageSlot = damageSlot,
                    save = dto.dc?.let { "${it.dc_type.name}(${it.dc_success})" },
                    school = MagicSchool.fromIndex(dto.school?.index.toString())
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