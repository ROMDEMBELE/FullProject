package repository

import data.api.DndApi
import data.sql_database.Database
import domain.Alignment
import domain.Challenge
import domain.CreatureSize
import domain.CreatureType
import domain.Monster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MonsterRepository(private val dndApi: DndApi, private val database: Database) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadMonsterDatabaseByChallenge()
        }
    }

    fun setMonsterFavorite(index: String, isFavorite: Boolean) {
        if (isFavorite) database.setMonsterIsFavorite(index) else database.setMonsterIsNotFavorite(
            index
        )
    }

    fun getFavoriteMonsters(): Flow<List<Monster>> = database.getAllMonsters().map {
        it.filter { it.isFavorite == 1L }
            .map { dbo ->
                Monster(
                    index = dbo.id,
                    name = dbo.name,
                    challenge = Challenge.fromDouble(dbo.challenge),
                    isFavorite = dbo.isFavorite == 1L
                )
            }
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
            return Monster(
                index = dto.index,
                name = dto.name,
                size = CreatureSize.fromString(dto.size.toString()),
                type = CreatureType.fromString(dto.type.toString()),
                alignment = Alignment.fromString(dto.alignment.toString()),
                armorClass = dto.armorClass?.first()?.let { "${it.value} (${it.type})" },
                hitPoints = dto.hitPoints,
                hitDice = dto.hitDice,
                hitPointsRoll = dto.hitPointsRoll,
                walkSpeed = dto.speed?.walk?.dropLast(3)?.toInt(),
                flySpeed = dto.speed?.fly?.dropLast(3)?.toInt(),
                swimSpeed = dto.speed?.walk?.dropLast(3)?.toInt(),
                strength = dto.strength,
                dexterity = dto.dexterity,
                constitution = dto.constitution,
                intelligence = dto.intelligence,
                wisdom = dto.wisdom,
                charisma = dto.charisma,
                proficiencies = dto.proficiencies.orEmpty()
                    .map { it.proficiency.name + "(${it.value})" },
                damageVulnerabilities = dto.damageVulnerabilities.orEmpty(),
                damageResistances = dto.damageResistances.orEmpty(),
                damageImmunities = dto.damageImmunities.orEmpty(),
                conditionImmunities = dto.conditionImmunities.orEmpty().map { it.name },
                //senses = dto.senses.orEmpty().map { it.name },
                languages = dto.languages,
                challenge = Challenge.fromDouble(dto.challengeRating ?: 0.0),
                proficiencyBonus = dto.proficiencyBonus,
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
                database.insertOrUpdateMonster(
                    index = dto.index, name = dto.name, challenge = it.rating, isFavorite = false
                )
            }
        }
    }
}