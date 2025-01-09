package data.repository

import common.safeGetBoolean
import common.safeGetDouble
import common.safeGetInt
import common.safeGetJsonArray
import common.safeGetJsonObject
import common.safeGetString
import common.safeLet
import data.api.MonsterApi
import data.api.dto.SearchResultDto
import data.database.sqlDelight.SqlDatabase
import domain.model.Alignment
import domain.model.Condition
import domain.model.DamageType
import domain.model.Environment
import domain.model.monster.Action
import domain.model.monster.Challenge
import domain.model.monster.CreatureSize
import domain.model.monster.CreatureType
import domain.model.monster.Monster
import domain.model.monster.Trait
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.dembeyo.data.MonsterDbo
import org.koin.core.error.MissingPropertyException

class MonsterRepositoryImpl(
    private val monsterApi: MonsterApi,
    private val database: SqlDatabase
) : MonsterRepository {

    private fun JsonObject.toAction(): Action? {
        try {
            return Action(name = this.safeGetString("name"),
                desc = this.safeGetString("desc"),
                usage = this["uses_type"]?.jsonPrimitive?.contentOrNull,
                useDice = this["uses_param"]?.jsonPrimitive?.intOrNull,
                legendaryCost = this["legendary_cost"]?.jsonPrimitive?.intOrNull,
                attacks = this.safeGetJsonArray("attacks").mapNotNull { attack ->
                    attack.jsonObject.let { attackObj ->
                        val dieCount = attackObj["damage_die_count"]?.jsonPrimitive?.intOrNull
                        val dieType = attackObj["damage_die_type"]?.jsonPrimitive?.contentOrNull
                        val attackBonus = attackObj["damage_bonus"]?.jsonPrimitive?.intOrNull

                        val damageType =
                            attackObj["damage_type"]?.jsonPrimitive?.contentOrNull?.let {
                                DamageType.fromString(it)
                            } ?: attackObj["extra_damage_type"]?.jsonPrimitive?.contentOrNull?.let {
                                DamageType.fromString(it)
                            }

                        Action.Attack(
                            type = attackObj.safeGetString("attack_type"),
                            attackBonus = attackObj.safeGetInt("to_hit_mod"),
                            reach = attackObj["reach"]?.jsonPrimitive?.doubleOrNull,
                            range = attackObj["range"]?.jsonPrimitive?.doubleOrNull,
                            longRange = attackObj["long_range"]?.jsonPrimitive?.doubleOrNull,
                            damageDice = safeLet(
                                dieCount,
                                dieType,
                                attackBonus
                            ) { count, type, bonus -> "$count$type+$bonus" } ?: "???",
                            damageType = damageType
                                ?: throw MissingPropertyException("Missing damage type property")
                        )
                    }
                })
        } catch (e: MissingPropertyException) {
            println("Error while parsing action: ${e.message}")
            return null
        }
    }

    @Throws(MissingPropertyException::class)
    private fun JsonObject.toMonster(): Monster {
        val abilities = this.safeGetJsonObject("ability_scores")
        val savingThrows = this.safeGetJsonObject("saving_throws_all")
        val speed = this.safeGetJsonObject("speed_all")
        val actions = this.safeGetJsonArray("actions")

        return Monster(isFavorite = false, // Ajoutez la logique pour déterminer si le monstre est favori
            key = this.safeGetString("key"),
            name = this.safeGetString("name"),
            challenge = this.safeGetDouble("challenge_rating_decimal")
                .let { Challenge.fromRating(it) },
            size = this.safeGetString("size").let { CreatureSize.fromUrl(it) },
            type = this.safeGetString("type").let { CreatureType.fromUrl(it) },
            alignment = this.safeGetString("alignment").let { Alignment.fromString((it)) },

            armorsClass = this.safeGetInt("armor_class"),
            hitPoints = this.safeGetInt("hit_points"),

            // Ability score
            wisdom = abilities.safeGetInt("wisdom"),
            wisdomSave = savingThrows.safeGetInt("wisdom"),
            strength = abilities.safeGetInt("strength"),
            strengthSave = savingThrows.safeGetInt("strength"),
            constitution = abilities.safeGetInt("constitution"),
            constitutionSave = savingThrows.safeGetInt("constitution"),
            intelligence = abilities.safeGetInt("intelligence"),
            intelligenceSave = savingThrows.safeGetInt("intelligence"),
            dexterity = abilities.safeGetInt("dexterity"),
            dexteritySave = savingThrows.safeGetInt("dexterity"),
            charisma = abilities.safeGetInt("charisma"),
            charismaSave = savingThrows.safeGetInt("charisma"),

            // Speed
            walkSpeed = speed.safeGetDouble("walk"),
            swimSpeed = speed.safeGetDouble("swim"),
            flySpeed = speed.safeGetDouble("fly"),
            climbSpeed = speed.safeGetDouble("climb"),
            burrowSpeed = speed.safeGetDouble("burrow"),
            hover = speed.safeGetBoolean("hover"),

            // Perception
            passivePerception = this.safeGetInt("passive_perception"),
            sightRange = this["normal_sight_range"]?.jsonPrimitive?.doubleOrNull ?: 0.0,
            darkVisionRange = this["darkvision_range"]?.jsonPrimitive?.doubleOrNull,
            blindSightRange = this["blindsight_range"]?.jsonPrimitive?.doubleOrNull,
            tremorSenseRange = this["tremorsense_range"]?.jsonPrimitive?.doubleOrNull,
            trueSightRange = this["truesight_range"]?.jsonPrimitive?.doubleOrNull,

            // Languages
            languages = this["languages"]?.let {
                when (it) {
                    is JsonArray -> {
                        it.mapNotNull { language ->
                            language.jsonObject["name"]?.jsonPrimitive?.contentOrNull
                                ?: throw MissingPropertyException("Missing language property")
                        }
                    }

                    is JsonPrimitive -> {
                        listOf(it.contentOrNull ?: "")
                    }

                    else -> {
                        emptyList()
                    }
                }
            } ?: emptyList(),

            // Resistance and immunities

            nonMagicalAttackImmunity = this.safeGetBoolean("nonmagical_attack_immunity"),
            nonMagicalAttackResistance = this.safeGetBoolean("nonmagical_attack_resistance"),

            damageImmunities = this.safeGetJsonArray("damage_immunities").mapNotNull {
                it.jsonPrimitive.contentOrNull?.let { content ->
                    DamageType.fromUrl(content)
                }
            },
            damageResistances = this.safeGetJsonArray("damage_resistances").mapNotNull {
                it.jsonPrimitive.contentOrNull?.let { content ->
                    DamageType.fromUrl(content)
                }
            },
            damageVulnerabilities = this.safeGetJsonArray("damage_vulnerabilities").mapNotNull {
                it.jsonPrimitive.contentOrNull?.let { content ->
                    DamageType.fromUrl(content)
                }
            },
            conditionImmunities = this.safeGetJsonArray("condition_immunities").mapNotNull {
                it.jsonPrimitive.contentOrNull?.let { content ->
                    Condition.fromUrl(content)
                }
            },
            traits = this.safeGetJsonArray("traits").map { trait ->
                Trait(
                    name = trait.jsonObject.safeGetString("name"),
                    desc = trait.jsonObject.safeGetString("desc")
                )
            },
            actions = actions
                .filterIsInstance<JsonObject>()
                .filter { it.safeGetString("action_type") == "ACTION" }
                .mapNotNull { it.toAction() },
            reactions = actions
                .filterIsInstance<JsonObject>()
                .filter { it.safeGetString("action_type") == "REACTION" }
                .mapNotNull { it.toAction() },
            bonusActions = actions
                .filterIsInstance<JsonObject>()
                .filter { it.safeGetString("action_type") == "BONUS_ACTION" }
                .mapNotNull { it.toAction() },
            legendaryActions = actions
                .filterIsInstance<JsonObject>()
                .filter { it.safeGetString("action_type") == "LEGENDARY_ACTION" }
                .mapNotNull { it.toAction() },

            environments = this.safeGetJsonArray("environments").mapNotNull {
                when (it) {
                    is JsonPrimitive -> it.contentOrNull?.let { content ->
                        Environment.fromString(
                            content
                        )
                    }

                    is JsonObject -> it.safeGetString("name")
                        .let { name -> Environment.fromString(name) }

                    else -> null
                }
            }
        )
    }

    private fun MonsterDbo.toMonster(): Monster {
        return Monster(
            isFavorite = true,
            key = key,
            name = name,
            challenge = challenge,
            size = size,
            type = type,
            alignment = alignment,
            armorsClass = armors_class.toInt(),
            hitPoints = hit_points.toInt(),
            wisdom = wisdom.toInt(),
            wisdomSave = wisdom_save?.toInt(),
            strength = strength.toInt(),
            strengthSave = strength_save?.toInt(),
            constitution = constitution.toInt(),
            constitutionSave = constitution_save?.toInt(),
            intelligence = intelligence.toInt(),
            intelligenceSave = intelligence_save?.toInt(),
            dexterity = dexterity.toInt(),
            dexteritySave = dexterity_save?.toInt(),
            charisma = charisma.toInt(),
            charismaSave = charisma_save?.toInt(),
            walkSpeed = walk_speed,
            swimSpeed = swim_speed,
            flySpeed = fly_speed,
            climbSpeed = climb_speed,
            burrowSpeed = burrow_speed,
            hover = hover,
            passivePerception = passive_perception.toInt(),
            sightRange = sight_range,
            darkVisionRange = dark_vision_range,
            blindSightRange = blind_sight_range,
            tremorSenseRange = tremor_sense_range,
            trueSightRange = true_sight_range,
            languages = languages,
            nonMagicalAttackImmunity = non_magical_attack_immunity,
            nonMagicalAttackResistance = non_magical_attack_resistance,
            damageImmunities = damage_immunities,
            damageResistances = damage_resistances,
            damageVulnerabilities = damage_vulnerabilities,
            conditionImmunities = condition_immunities,
            traits = traits,
            actions = actions,
            reactions = reactions,
            bonusActions = bonus_actions,
            legendaryActions = legendary_actions,
            environments = environments
        )
    }

    private fun fetchMonsters(call: suspend () -> SearchResultDto<JsonObject>): Flow<List<Monster>> =
        flow {
            var searchResult = call()
            do {
                emit(searchResult.results.map { it.toMonster() })
                searchResult.next?.let { next ->
                    searchResult = monsterApi.getNextPage(next)
                }
            } while (searchResult.next != null)
        }

    override suspend fun getFavorites(): Flow<List<Monster>> {
        return database.getAllMonsters().map { monsterDboList ->
            monsterDboList.map { it.toMonster() }
        }
    }

    override suspend fun getFavorite(key: String): Monster? {
        return database.getMonsterById(key)?.toMonster()
    }

    override suspend fun addFavorite(monster: Monster) {
        database.insertOrUpdateMonster(monster)
    }

    override suspend fun removeFavorite(key: String) {
        database.deleteMonsterById(key)
    }

    override suspend fun search(
        name: String, min: Challenge, max: Challenge
    ): Flow<List<Monster>> {
        // Search monsters in API with name, min and max challenge
        return fetchMonsters { monsterApi.search(name, min.rating, max.rating) }.map { list ->
            list.distinctBy { it.key }
                .distinctBy { it.name }
        }
    }

    override suspend fun getByKey(slug: String): Monster {
        // TODO get from local data base
        val searchResult = monsterApi.getByKey(slug)

        if (searchResult.results.isEmpty()) {
            throw NoSuchElementException("Monster with slug $slug not found")
        }
        return searchResult.results.first().toMonster()
    }

    companion object {
        private const val FORMAT_FILE = "monster.json"
    }
}