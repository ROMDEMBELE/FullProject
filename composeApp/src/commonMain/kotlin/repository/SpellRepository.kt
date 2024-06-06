package repository

import data.FavoriteSpell
import data.RealmDataBase
import data.api.DndApi
import domain.Level
import domain.MagicSchool
import domain.Spell
import io.ktor.client.plugins.ServerResponseException
import org.lighthousegames.logging.logging

class SpellRepository(private val spellApi: DndApi, private val dataBase: RealmDataBase) {

    suspend fun addFavorite(spell: Spell) {
        dataBase.saveFavoriteSpell(FavoriteSpell(spell.index))
    }

    suspend fun removeFavorite(spell: Spell) {
        dataBase.deleteFavoriteSpell(spell.index)
    }

    suspend fun searchSpell(
        filterByLevel: List<Level>,
        filterBySchool: List<MagicSchool>
    ): List<Spell> {
        try {
            // Get fav
            val favorite = dataBase.getFavoriteSpells()

            val searchResult = spellApi.getSpellByLevelOrSchool(
                levelList = filterByLevel.map { it.level.toString() },
                schoolList = filterBySchool.map { it.index }
            )
            return searchResult.results.map { dto ->
                Spell(
                    index = dto.index,
                    name = dto.name,
                    isFavorite = favorite.any { fav -> dto.index == fav.index },
                    level = Level.fromInt(dto.level),
                )
            }
        } catch (e: ServerResponseException) {
            Log.e { e.message }
            return emptyList()
        }
    }

    suspend fun getOneSpell(index: String): Spell? {
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
                val isFavorite: Boolean = dataBase.findFavoriteSpell(index) != null
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