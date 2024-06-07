package repository

import data.api.DndApi
import data.database.SqlDatabase
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
                armorClass = dto.armor_class?.first()?.let { "${it.value} (${it.type})" },
                hitPoints = dto.hit_points,
                hitPointsRoll = dto.hit_points_roll,
                walkSpeed = dto.speed?.walk?.dropLast(4)?.toInt(),
                flySpeed = dto.speed?.fly?.dropLast(4)?.toInt(),
                swimSpeed = dto.speed?.walk?.dropLast(4)?.toInt(),
                strength = dto.strength,
                dexterity = dto.dexterity,
                constitution = dto.constitution,
                intelligence = dto.intelligence,
                wisdom = dto.wisdom,
                charisma = dto.charisma,
                proficiencies = dto.proficiencies.orEmpty()
                    .map { it.proficiency.name + "(${it.value})" },
                damageVulnerabilities = dto.damage_vulnerabilities.orEmpty(),
                damageResistances = dto.damage_resistances.orEmpty(),
                damageImmunities = dto.damage_immunities.orEmpty(),
                conditionImmunities = dto.condition_immunities.orEmpty().map { it.name },
                //senses = dto.senses.orEmpty().map { it.name },
                languages = dto.languages,
                challenge = Challenge.fromDouble(dto.challenge_rating ?: 0.0),
                proficiencyBonus = dto.proficiency_bonus,
                xp = dto.xp,
                image = dto.image,
                //specialAbilities = dto.specialAbilities.map { it.toDomainModel() },
                //actions = dto.actions.map { it.toDomainModel() },
                //legendaryActions = dto.legendaryActions.map { it.toDomainModel() }
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