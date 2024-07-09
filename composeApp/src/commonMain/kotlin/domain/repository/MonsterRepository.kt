package domain.repository

import data.api.Dnd5Api
import data.database.SqlDatabase
import data.dto.monster.MonsterDto
import data.dto.monster.PolymorphicAbility
import data.dto.monster.PolymorphicAction
import data.dto.monster.PolymorphicDamage
import data.dto.monster.PolymorphicMultiAttackOption
import data.dto.monster.PolymorphicSpellCastingAbilityDetails
import data.dto.monster.PolymorphicUsageLimitDto
import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import domain.model.SavingThrow
import domain.model.monster.Action
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.model.monster.SpecialAbility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.dembeyo.data.MonsterDbo
import org.lighthousegames.logging.logging

class MonsterRepository(private val dndApi: Dnd5Api, private val database: SqlDatabase) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchData()
        }
    }

    private fun MonsterDbo.toDomain() = Monster(
        index = id,
        name = name,
        challenge = Challenge.fromDouble(challenge),
        isFavorite = isFavorite == 1L
    )

    private fun MonsterDto.toDomain(isFavorite: Boolean): Monster {
        val savingThrows = mutableListOf<SavingThrow>()
        val skills = mutableListOf("Proficiency Bonus $proficiencyBonus")
        proficiencies.forEach {
            when {
                it.proficiency.name.contains("Skill") -> {
                    skills += "${it.proficiency.name.removePrefix("Skill: ")} ${it.value}"
                }

                it.proficiency.name.contains("Saving") -> {
                    SavingThrow(
                        value = it.value,
                        ability = Ability.valueOf(it.proficiency.name.removePrefix("Saving Throw: ")),
                    )
                }
            }
        }
        val specialAbilities: List<SpecialAbility> =
            specialAbilities.mapNotNull { dto -> dto.toDomain() }

        return Monster(
            index = index,
            name = name,
            isFavorite = isFavorite,
            challenge = Challenge.fromDouble(challengeRating),
            details = Monster.Details(
                size = size,
                type = type,
                alignment = alignment,
                armorsClass = buildMap {
                    armorClass.forEach { put(it.type, it.value) }
                },
                hitPoints = hitPoints,
                hitPointsRoll = hitPointsRoll,
                strength = strength,
                dexterity = dexterity,
                constitution = constitution,
                intelligence = intelligence,
                wisdom = wisdom,
                charisma = charisma, speedByMovements = speed,
                skills = skills,
                savingThrows = savingThrows,
                damageVulnerabilities = damageVulnerabilities,
                damageResistances = damageResistances,
                damageImmunities = damageImmunities,
                conditionImmunities = conditionImmunities.map { it.name },
                senses = senses,
                languages = languages,
                xp = xp,
                image = image,
                specialAbilities = specialAbilities,
                actions = actions.map { it.toDomain() },
                legendaryActions = legendaryActions.map { it.toDomain() }
            )
        )
    }

    private fun PolymorphicUsageLimitDto.toDomain(): String = when (this) {
        is PolymorphicUsageLimitDto.AtWill -> "at will"
        is PolymorphicUsageLimitDto.PerDay -> "$times per day"
        is PolymorphicUsageLimitDto.RechargeOnRoll -> "recharge on roll $minValue $dice"
        else -> throw IllegalArgumentException("Unknown usage type")
    }

    private fun PolymorphicAbility.toDomain(): SpecialAbility? {
        return when (this) {
            is PolymorphicAbility.SpecialAbilityDto -> SpecialAbility(name, desc)
            is PolymorphicAbility.SavingThrowAbilityDto -> SpecialAbility.SavingThrowAbility(
                name = name,
                desc = desc,
                savingThrow = SavingThrow(
                    value = dc.dcValue,
                    ability = Ability.valueOf(dc.typeOfDc.name),
                    success = dc.successType
                )
            )

            is PolymorphicAbility.SpellCastingAbilityDto -> when (val details = spellCasting) {
                is PolymorphicSpellCastingAbilityDetails.InnateSpellCasting -> {
                    SpecialAbility.InnateSpellCastingAbility(
                        name = name,
                        desc = desc,
                        savingThrow = SavingThrow(
                            value = details.dc,
                            ability = Ability.valueOf(details.ability.name),
                        ),
                        components = details.componentsRequired,
                        spellByUsage = details.spells.map { sp ->
                            SpecialAbility.SpellCasting(
                                level = Level.fromInt(sp.level),
                                name = sp.name,
                                notes = sp.notes,
                                index = sp.url.split("/").last(),
                                usage = sp.usage?.toDomain(),
                            )
                        }.groupBy { it.usage.toString() }
                    )
                }

                is PolymorphicSpellCastingAbilityDetails.MagicianSpellActing -> {
                    SpecialAbility.SpellCastingAbility(
                        name = name,
                        desc = desc,
                        savingThrow = SavingThrow(
                            value = details.dc,
                            ability = Ability.valueOf(details.ability.name),
                        ),
                        components = details.componentsRequired,
                        level = Level.fromInt(details.level),
                        school = details.school,
                        modifier = details.modifier,
                        slots = details.slots.mapKeys { (level, _) ->
                            Level.fromInt(
                                level
                            )
                        },
                        dc = details.dc,
                        ability = Ability.valueOf(details.ability.name),
                        spellByLevel = details.spells.map { sp ->
                            SpecialAbility.SpellCasting(
                                level = Level.fromInt(sp.level),
                                name = sp.name,
                                index = sp.url.split("/").last(),
                                notes = sp.notes,
                                usage = sp.usage?.toDomain()
                            )
                        }.groupBy { it.level }
                    )
                }

                else -> null
            }
        }
    }

    private fun PolymorphicDamage.toDomain(): List<Action.Damage> {
        return when (this) {
            is PolymorphicDamage.DamageDto -> {
                listOf(
                    Action.Damage(
                        type = DamageType.fromIndex(damageType.index),
                        dice = damageDice,
                        notes = notes
                    )
                )
            }

            is PolymorphicDamage.DamageOptionContainer -> {
                from.options.flatMap { it.toDomain() }
            }

            else -> throw IllegalArgumentException("Unknown damage type")
        }
    }

    private fun PolymorphicAction.toDomain(): Action {
        return when (this) {

            is PolymorphicAction.SimpleActionDto -> {
                Action(
                    name = name,
                    desc = desc,
                    usage = usage?.toDomain()
                )
            }

            is PolymorphicAction.AttackActionDto -> {
                Action.AttackAction(
                    name = name,
                    attackBonus = attackBonus,
                    desc = desc,
                    damage = damage.flatMap { it.toDomain() }
                )
            }

            is PolymorphicAction.MultiAttackActionDto -> {
                val attacks = when (attackOption) {
                    null -> actions.map { it.actionName }
                    else -> attackOption.from.options.flatMap { option ->
                        when (option) {
                            is PolymorphicMultiAttackOption.OptionAction -> listOf(option.actionName)
                            is PolymorphicMultiAttackOption.OptionMultiple -> option.items.map { it.actionName }
                            else -> throw IllegalArgumentException("Unknown option type: $option")
                        }
                    }
                }
                Action.MultiAttackAction(
                    name = name,
                    desc = desc,
                    choose = attackOption?.choose ?: 0,
                    attacks = attacks
                )
            }

            is PolymorphicAction.SavingThrowActionDto -> {
                Action.SavingThrowAction(
                    name = name,
                    desc = desc,
                    savingThrow = SavingThrow(
                        value = dc.dcValue,
                        ability = Ability.valueOf(dc.typeOfDc.name),
                        success = dc.successType
                    ),
                    damage = damage.orEmpty().flatMap { it.toDomain() },
                    // recharge on roll 5(1d6)
                    usage = usage?.toDomain()
                )
            }
        }
    }

    suspend fun fetchData() {
        Challenge.entries.forEach {
            try {
                dndApi.getMonstersByChallenge(it.rating).results.forEach { dto ->
                    database.insertMonster(
                        index = dto.index,
                        name = dto.name,
                        challenge = it.rating,
                    )
                }
            } catch (e: Exception) {
                Log.w { "Unable to update the monster database" }
            }
        }
    }

    fun setFavorite(index: String, isFavorite: Boolean) =
        database.updateMonsterFavoriteStatus(index, isFavorite)

    fun getAll(): Flow<List<Monster>> =
        database.getAllMonsters().map { it.map { dbo -> dbo.toDomain() } }

    suspend fun getByIndex(index: String): Monster? {
        try {
            val monsterDto = dndApi.getMonsterByIndex(index)
            val isFavorite: Boolean = database.getMonsterById(index).firstOrNull()?.isFavorite == 1L
            return monsterDto.toDomain(isFavorite)
        } catch (e: Exception) {
            Log.e { e.message.toString() }
            return null
        }
    }

    companion object {
        val Log = logging("MonsterRepository")
    }
}