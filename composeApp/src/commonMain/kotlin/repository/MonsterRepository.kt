package repository

import data.api.DndApi
import data.database.SqlDatabase
import data.dto.MonsterDto
import domain.Alignment
import domain.Challenge
import domain.CreatureSize
import domain.CreatureType
import domain.Monster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
        return dndApi.getMonsterByIndex(index)?.let { dto ->
            val isFavorite: Boolean = database.getMonsterById(index).firstOrNull()?.isFavorite == 1L
            return Monster(
                index = dto.index,
                name = dto.name,
                isFavorite = isFavorite,
                size = CreatureSize.fromString(dto.size.toString()),
                type = CreatureType.fromString(dto.type.toString()),
                alignment = Alignment.fromString(dto.alignment.toString()),
                armorClass = dto.armorClass?.first()?.toString(),
                hitPoints = dto.hitPoints,
                hitPointsRoll = dto.hitPointsRoll,
                walkSpeed = dto.speed?.walk?.dropLast(4)?.toInt(),
                flySpeed = dto.speed?.fly?.dropLast(4)?.toInt(),
                swimSpeed = dto.speed?.walk?.dropLast(4)?.toInt(),
                strength = dto.strength,
                dexterity = dto.dexterity,
                constitution = dto.constitution,
                intelligence = dto.intelligence,
                wisdom = dto.wisdom,
                charisma = dto.charisma,
                proficiencies = dto.proficiencies.orEmpty().map { "${it.proficiency.name} ${it.value})" },
                damageVulnerabilities = dto.damageVulnerabilities.orEmpty(),
                damageResistances = dto.damageResistances.orEmpty(),
                damageImmunities = dto.damageImmunities.orEmpty(),
                conditionImmunities = dto.conditionImmunities.orEmpty().map { it.name },
                senses = dto.senses?.toString(),
                languages = dto.languages,
                challenge = Challenge.fromDouble(dto.challengeRating ?: 0.0),
                proficiencyBonus = dto.proficiencyBonus,
                xp = dto.xp,
                image = dto.image,
                specialAbilities = dto.specialAbilities.orEmpty()
                    .map { Monster.SpecialAbility(it.name, it.desc) },
                actions = dto.actions.orEmpty().map { it.toDomainModel() },
                //legendaryActions = dto.legendaryActions.map { it.toDomainModel() }
            )
        }
    }

    private fun getListOfDamageFromDto(list: List<MonsterDto.DamageDto>): List<Monster.Damage> {
        return list.flatMap { dmg ->
            when {
                dmg.damageDice != null && dmg.damageType != null -> listOf(Monster.Damage(dmg.damageType.name, dmg.damageDice))
                dmg.from != null -> dmg.from.options.map {
                    Monster.Damage(it.damageType.name, it.damageDice, it.notes)
                }
                else -> emptyList()
            }
        }
    }

    private fun MonsterDto.ActionDto.toDomainModel(): Monster.Action {
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
}