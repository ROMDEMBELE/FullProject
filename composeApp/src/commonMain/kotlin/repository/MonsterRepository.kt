package repository

import data.api.DndApi
import data.database.SqlDatabase
import domain.Ability
import domain.Challenge
import domain.Monster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class MonsterRepository(private val dndApi: DndApi, private val database: SqlDatabase) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadMonsterDatabaseByChallenge()
        }
    }

    fun setMonsterIsFavorite(index: String, isFavorite: Boolean) {
        database.updateMonsterFavoriteStatus(index, isFavorite)
    }

    fun getMonsters(): Flow<List<Monster>> = database.getAllMonsters().map {
        it.map { dbo ->
            Monster(
                index = dbo.id,
                name = dbo.name,
                challenge = Challenge.fromDouble(dbo.challenge),
                isFavorite = dbo.isFavorite == 1L
            )
        }
    }

    suspend fun getMonsterByIndex(index: String): Monster? {
        try {
            return dndApi.getMonsterByIndex(index)?.let { dto ->
                val savingThrows = mutableListOf<String>()
                val skills = mutableListOf<String>()
                if (dto.proficiencyBonus != null) {
                    skills.add("Proficiency Bonus ${dto.proficiencyBonus}")
                }
                dto.proficiencies.orEmpty().forEach {
                    if (it.proficiency.name.contains("Skill")) {
                        skills += "${it.proficiency.name.removePrefix("Skill: ")} ${it.value}"
                    } else if (it.proficiency.name.contains("Saving")) {
                        savingThrows += "${it.proficiency.name.removePrefix("Saving Throw: ")} ${it.value}"
                    }
                }
                val isFavorite: Boolean =
                    database.getMonsterById(index).firstOrNull()?.isFavorite == 1L

                val abilities = buildMap {
                    put(Ability.STR, dto.strength ?: 0)
                    put(Ability.DEX, dto.dexterity ?: 0)
                    put(Ability.CON, dto.constitution ?: 0)
                    put(Ability.INT, dto.intelligence ?: 0)
                    put(Ability.WIS, dto.wisdom ?: 0)
                    put(Ability.CHA, dto.charisma ?: 0)
                }

                return Monster(
                    index = dto.index,
                    name = dto.name,
                    isFavorite = isFavorite,
                    size = dto.size,
                    type = dto.type,
                    alignment = dto.alignment,
                    armors = buildMap { dto.armorClass.orEmpty().forEach { put(it.type, it.value) } },
                    hitPoints = dto.hitPoints,
                    hitPointsRoll = dto.hitPointsRoll,
                    abilities = abilities,
                    movements = dto.speed,
                    skills = skills,
                    savingThrows = savingThrows,
                    damageVulnerabilities = dto.damageVulnerabilities.orEmpty(),
                    damageResistances = dto.damageResistances.orEmpty(),
                    damageImmunities = dto.damageImmunities.orEmpty(),
                    conditionImmunities = dto.conditionImmunities.orEmpty().map { it.name },
                    senses = dto.senses?.toString(),
                    languages = dto.languages,
                    challenge = Challenge.fromDouble(dto.challengeRating ?: 0.0),
                    xp = dto.xp,
                    image = dto.image,
                    specialAbilities = dto.specialAbilities.orEmpty()
                        .map { Monster.SpecialAbility(it.name, it.desc) },
                    //actions = dto.actions.orEmpty().map { it.toDomainModel() },
                    //legendaryActions = dto.legendaryActions.map { it.toDomainModel() }
                )
            }
        } catch (e: Exception) {
            Log.e { e.message.toString() }
            return null
        }
    }
    /*

    private fun getListOfDamageFromDto(list: List<MonsterDto.DamageDto>): List<Monster.Damage> {
        return list.flatMap { dmg ->
            when {
                dmg.damageDice != null && dmg.damageType != null -> listOf(
                    Monster.Damage(
                        dmg.damageType.name,
                        dmg.damageDice
                    )
                )

                dmg.from != null -> dmg.from.options.map {
                    Monster.Damage(it.damageType.name, it.damageDice, it.notes)
                }

                else -> emptyList()
            }
        }
    }

    private fun PolymorphicAction.toDomainModel(): Monster.Action {
        if (multiAttackType == "action_options") {
            return Monster.SimpleAction(
                name = name,
                desc = desc,
            )
        } else if (damage.isNullOrEmpty()) {
            return Monster.SimpleAction(
                name = name,
                desc = desc,
            )
        } else if (attackBonus == null) {
            // Damage Parsing
            return Monster.PowerAction(
                name = name,
                desc = desc,
                save = dc?.toString().toString(),
                recharge = buildString {
                    append(usage?.type)
                    append(" : ")
                    append(usage?.min_value)
                    append('(' + usage?.dice.toString() + ')')
                },
                damage = getListOfDamageFromDto(damage)
            )
        } else {
            return Monster.AttackAction(
                name = name,
                bonus = attackBonus,
                desc = desc,
                damage = getListOfDamageFromDto(damage)
            )
        }
    }
    */

    private suspend fun loadMonsterDatabaseByChallenge() {
        Challenge.entries.forEach {
            val result = dndApi.getMonstersByChallenge(it.rating)
            result.results.forEach { dto ->
                database.createMonster(
                    index = dto.index,
                    name = dto.name,
                    challenge = it.rating,
                )
            }
        }
    }

    companion object {
        val Log = logging("MonsterRepository")
    }
}