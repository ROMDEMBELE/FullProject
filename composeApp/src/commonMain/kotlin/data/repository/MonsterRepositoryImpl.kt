package data.repository

import data.api.ChatGptApi
import data.api.MonsterApi
import data.api.dto.MonsterDto
import data.api.dto.SearchResultDto
import data.api.dto.chatGpt.ChatGptResponseFormatDto
import data.local.LocalDatasource
import domain.model.Alignment
import domain.model.CreatureSize
import domain.model.CreatureType
import domain.model.DamageType
import domain.model.Environment
import domain.model.monster.Action
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.model.monster.MonsterReference
import domain.model.monster.Trait
import domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class MonsterRepositoryImpl(
    private val monsterApi: MonsterApi,
    private val chatGptApi: ChatGptApi,
    private val local: LocalDatasource
) : MonsterRepository {

    fun JsonObject.toMonster(): Monster? {
        return try {
            Monster(
                key = this["key"]?.jsonPrimitive?.contentOrNull ?: return null,
                name = this["name"]?.jsonPrimitive?.contentOrNull ?: return null,
                isFavorite = false, // Ajoutez la logique pour déterminer si le monstre est favori
                challenge = this["challenge_rating_decimal"]?.jsonPrimitive?.contentOrNull?.toDoubleOrNull()?.let {
                    Challenge.fromDecimal(it)
                } ?: return null,
                size = CreatureSize.fromUrl(this["size"]?.jsonPrimitive?.contentOrNull ?: return null),
                type = CreatureType.fromUrl(this["type"]?.jsonPrimitive?.contentOrNull ?: return null),
                alignment = Alignment.fromString(this["alignment"]?.jsonPrimitive?.contentOrNull ?: return null),
                armorsClass = this["armor_class"]?.jsonPrimitive?.intOrNull ?: return null,
                hitPoints = this["hit_points"]?.jsonPrimitive?.intOrNull ?: return null,
                charisma = this["ability_scores"]?.jsonObject?.get("charisma")?.jsonPrimitive?.intOrNull ?: return null,
                charismaSave = this["saving_throws"]?.jsonObject?.get("charisma")?.jsonPrimitive?.intOrNull,
                dexterity = this["ability_scores"]?.jsonObject?.get("dexterity")?.jsonPrimitive?.intOrNull ?: return null,
                dexteritySave = this["saving_throws"]?.jsonObject?.get("dexterity")?.jsonPrimitive?.intOrNull,
                constitution = this["ability_scores"]?.jsonObject?.get("constitution")?.jsonPrimitive?.intOrNull ?: return null,
                constitutionSave = this["saving_throws"]?.jsonObject?.get("constitution")?.jsonPrimitive?.intOrNull,
                intelligence = this["ability_scores"]?.jsonObject?.get("intelligence")?.jsonPrimitive?.intOrNull ?: return null,
                intelligenceSave = this["saving_throws"]?.jsonObject?.get("intelligence")?.jsonPrimitive?.intOrNull,
                strength = this["ability_scores"]?.jsonObject?.get("strength")?.jsonPrimitive?.intOrNull ?: return null,
                strengthSave = this["saving_throws"]?.jsonObject?.get("strength")?.jsonPrimitive?.intOrNull,
                wisdom = this["ability_scores"]?.jsonObject?.get("wisdom")?.jsonPrimitive?.intOrNull ?: return null,
                wisdomSave = this["saving_throws"]?.jsonObject?.get("wisdom")?.jsonPrimitive?.intOrNull,
                walkSpeed = this["speed_all"]?.jsonObject?.get("walk")?.jsonPrimitive?.doubleOrNull ?: 0.0,
                swimSpeed = this["speed_all"]?.jsonObject?.get("swim")?.jsonPrimitive?.doubleOrNull ?: 0.0,
                flySpeed = this["speed_all"]?.jsonObject?.get("fly")?.jsonPrimitive?.doubleOrNull ?: 0.0,
                climbSpeed = this["speed_all"]?.jsonObject?.get("climb")?.jsonPrimitive?.doubleOrNull ?: 0.0,
                burrowSpeed = this["speed_all"]?.jsonObject?.get("burrow")?.jsonPrimitive?.doubleOrNull ?: 0.0,
                hover = this["speed_all"]?.jsonObject?.get("hover")?.jsonPrimitive?.booleanOrNull ?: false,
                // Ajoutez le reste des champs avec des traitements similaires
                traits = this["traits"]?.jsonArray?.mapNotNull { trait ->
                    trait.jsonObject.let {
                        Trait(
                            name = it["name"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null,
                            desc = it["desc"]?.jsonPrimitive?.contentOrNull ?: ""
                        )
                    }
                } ?: emptyList(),
                actions = this["actions"]?.jsonArray?.mapNotNull { action ->
                    action.jsonObject.let {
                        Action(
                            name = it["name"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null,
                            desc = it["desc"]?.jsonPrimitive?.contentOrNull ?: "",
                            usage = it["uses_type"]?.jsonPrimitive?.contentOrNull ?: "",
                            legendaryCost = it["legendary_cost"]?.jsonPrimitive?.intOrNull,
                            attacks = it["attacks"]?.jsonArray?.mapNotNull { attack ->
                                attack.jsonObject.let { attackObj ->
                                    Action.Attack(
                                        type = attackObj["attack_type"]?.jsonPrimitive?.contentOrNull ?: "",
                                        damageDice = attackObj["damage_dice"]?.jsonPrimitive?.contentOrNull ?: "",
                                        damageType = DamageType.fromString(attackObj["extra_damage_type"]?.jsonPrimitive?.contentOrNull ?: ""),
                                        attackBonus = attackObj["to_hit_mod"]?.jsonPrimitive?.intOrNull ?: 0,
                                        reach = attackObj["reach"]?.jsonPrimitive?.doubleOrNull,
                                        range = attackObj["range"]?.jsonPrimitive?.doubleOrNull,
                                        longRange = attackObj["long_range"]?.jsonPrimitive?.doubleOrNull
                                    )
                                }
                            } ?: emptyList()
                        )
                    }
                } ?: emptyList(),
                environments = this["environments"]?.jsonArray?.mapNotNull {
                    Environment.fromString(it.jsonPrimitive.contentOrNull ?: "")
                } ?: emptyList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun MonsterDto.ActionDto.toAction() = Action(
        name = name,
        desc = description,
        usage = usage.name,
        attacks = attacks.map { attack ->
            Action.Attack(
                type = attack.attackType,
                attackBonus = attack.attackBonus,
                reach = attack.reach,
                range = attack.range,
                longRange = attack.longRange,
                damageDice = attack.damageDieCount,
                damageType = attack.damageType,
            )
        }
    )

    private fun MonsterDto.toMonster() = Monster(
        isFavorite = false,
        key = key,
        name = name,
        challenge = Challenge.entries.find { it.rating == challenge }
            ?: throw IllegalArgumentException("Challenge $challenge not found"),
        size = size,
        type = type,
        alignment = alignment,
        armorsClass = armorClass,
        hitPoints = hitPoints,
        charisma = charisma,
        charismaSave = charismaSave,
        dexterity = dexterity,
        dexteritySave = dexteritySave,
        constitution = constitution,
        constitutionSave = constitutionSave,
        intelligence = intelligence,
        intelligenceSave = intelligenceSave,
        strength = strength,
        strengthSave = strengthSave,
        wisdom = wisdom,
        wisdomSave = wisdomSave,
        walkSpeed = walk,
        swimSpeed = swim,
        flySpeed = fly,
        climbSpeed = climb,
        burrowSpeed = burrow,
        hover = hover,
        arcana = skills.arcana,
        acrobatics = skills.acrobatics,
        animalHandling = skills.animalHandling,
        insight = skills.insight,
        intimidation = skills.intimidation,
        deception = skills.deception,
        history = skills.history,
        investigation = skills.investigation,
        medicine = skills.medicine,
        nature = skills.nature,
        performance = skills.performance,
        sleightOfHand = skills.sleightOfHand,
        stealth = skills.stealth,
        survival = skills.survival,
        nonMagicalAttackResistance = nonMagicalAttackImmunity,
        nonMagicalAttackImmunity = nonMagicalAttackImmunity,
        damageVulnerabilities = damageImmunities,
        damageResistances = damageResistances,
        damageImmunities = damageImmunities,
        conditionImmunities = conditionImmunities,
        passivePerception = passivePerception,
        languages = languages,
        sightRange = normalSightRange,
        darkVisionRange = darkVisionRange,
        blindSightRange = blindSightRange,
        tremorSenseRange = tremorSenseRange,
        trueSightRange = trueSightRange,
        actions = actions.filter { it.actionType == MonsterDto.ActionType.ACTION }
            .map { it.toAction() },
        reactions = actions.filter { it.actionType == MonsterDto.ActionType.REACTION }
            .map { it.toAction() },
        bonusActions = actions.filter { it.actionType == MonsterDto.ActionType.BONUS_ACTION }
            .map { it.toAction() },
        legendaryActions = actions.filter { it.actionType == MonsterDto.ActionType.LEGENDARY_ACTION }
            .map { it.toAction() },
        traits = traits.map { Trait(it.name, it.description) },
        environments = environments
    )

    private fun fetchMonsters(call: suspend () -> SearchResultDto<JsonObject>): Flow<List<JsonObject>> =
        flow {
            var searchResult = call()
            do {
                emit(searchResult.results)
                searchResult.next?.let { next ->
                    searchResult = monsterApi.getNextPage(next)
                }
            } while (searchResult.next != null)
        }

    private fun jsonToMonsterReference(json: JsonObject): MonsterReference {
        val refKey: String = json.getValue("key").jsonPrimitive.content
        val refName: String = json.getValue("name").jsonPrimitive.content
        val refChallenge: Double =
            json.getValue("challenge_rating_decimal").jsonPrimitive.content.toDouble()

        return MonsterReference(
            key = refKey,
            name = refName,
            isFavorite = false,
            challenge = Challenge.fromRating(refChallenge)
        )
    }


    override suspend fun search(
        name: String,
        min: Challenge,
        max: Challenge
    ): Flow<List<MonsterReference>> {
        // Search monsters in API with name, min and max challenge
        return fetchMonsters { monsterApi.search(name, min.rating, max.rating) }
            .map { list ->
                list.map(::jsonToMonsterReference)
                    .distinctBy { it.key }
                    .distinctBy { it.name }
            }
    }

    override suspend fun getByKey(slug: String): Monster {
        // TODO get from local data base
        val searchResult = monsterApi.getByKey(slug)

        if (searchResult.results.isEmpty()) {
            throw NoSuchElementException("Monster with slug $slug not found")
        }

        val raw = searchResult.results.first()

        val json = local.readFile(FORMAT_FILE)

        val format = ChatGptResponseFormatDto(
            type = "json_schema",
            schema = Json.decodeFromString(json)
        )

        // Process monsters with chatGPT
        val monsterDto = chatGptApi.processMonster(raw, format)

        return monsterDto.toMonster()
    }

    companion object {
        private const val FORMAT_FILE = "monster.json"
    }
}