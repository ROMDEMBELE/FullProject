package domain.repository

import data.api.Dnd5Api
import data.database.SqlDatabase
import data.dto.monster.PolymorphicAbility
import data.dto.monster.PolymorphicAction
import data.dto.monster.PolymorphicDamage
import data.dto.monster.PolymorphicMultiAttackOption
import data.dto.monster.PolymorphicSpellCastingAbilityDetails
import data.dto.monster.PolymorphicUsageLimitDto
import domain.model.Ability
import domain.model.DamageType
import domain.model.Level
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.model.monster.Monster.InnateSpellCastingAbility
import domain.model.monster.Monster.SavingThrow
import domain.model.monster.Monster.SavingThrowAbility
import domain.model.monster.Monster.SpecialAbility
import domain.model.monster.Monster.SpellCasting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class MonsterRepository(private val dndApi: Dnd5Api, private val database: SqlDatabase) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadMonsterDatabaseByChallenge()
        }
    }

    fun setMonsterIsFavorite(index: String, isFavorite: Boolean) {
        database.updateMonsterFavoriteStatus(index, isFavorite)
    }

    fun getListOfMonsters(): Flow<List<Monster>> = database.getAllMonsters().map {
        it.map { dbo ->
            Monster(
                index = dbo.id,
                name = dbo.name,
                challenge = Challenge.fromDouble(dbo.challenge),
                isFavorite = dbo.isFavorite == 1L
            )
        }
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
            is PolymorphicAbility.SavingThrowAbilityDto -> SavingThrowAbility(
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
                    InnateSpellCastingAbility(
                        name = name,
                        desc = desc,
                        savingThrow = SavingThrow(
                            value = details.dc,
                            ability = Ability.valueOf(details.ability.name),
                        ),
                        components = details.componentsRequired,
                        spellByUsage = details.spells.map { sp ->
                            SpellCasting(
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
                    Monster.SpellCastingAbility(
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
                            SpellCasting(
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

    private fun PolymorphicDamage.toDomain(): List<Monster.Damage> {
        return when (this) {
            is PolymorphicDamage.DamageDto -> {
                listOf(
                    Monster.Damage(
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

    private fun PolymorphicAction.toDomain(): Monster.Action {
        return when (this) {

            is PolymorphicAction.SimpleActionDto -> {
                Monster.Action(
                    name = name,
                    desc = desc,
                    usage = usage?.toDomain()
                )
            }

            is PolymorphicAction.AttackActionDto -> {
                Monster.AttackAction(
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
                Monster.MultiAttackAction(
                    name = name,
                    desc = desc,
                    choose = attackOption?.choose ?: 0,
                    attacks = attacks
                )
            }

            is PolymorphicAction.SavingThrowActionDto -> {
                Monster.SavingThrowAction(
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

    suspend fun getMonsterByIndex(index: String): Monster.MonsterDetails? {
        try {
            return dndApi.getMonsterByIndex(index)?.let { monsterDto ->
                val savingThrows = mutableListOf<SavingThrow>()
                val skills = mutableListOf("Proficiency Bonus ${monsterDto.proficiencyBonus}")
                monsterDto.proficiencies.forEach {
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
                val isFavorite: Boolean =
                    database.getMonsterById(index).firstOrNull()?.isFavorite == 1L

                val specialAbilities: List<SpecialAbility> =
                    monsterDto.specialAbilities.mapNotNull { dto ->
                        dto.toDomain()
                    }

                return Monster.MonsterDetails(
                    index = monsterDto.index,
                    name = monsterDto.name,
                    isFavorite = isFavorite,
                    size = monsterDto.size,
                    type = monsterDto.type,
                    alignment = monsterDto.alignment,
                    armorsClass = buildMap {
                        monsterDto.armorClass.forEach { put(it.type, it.value) }
                    },
                    hitPoints = monsterDto.hitPoints,
                    hitPointsRoll = monsterDto.hitPointsRoll,
                    strength = monsterDto.strength,
                    dexterity = monsterDto.dexterity,
                    constitution = monsterDto.constitution,
                    intelligence = monsterDto.intelligence,
                    wisdom = monsterDto.wisdom,
                    charisma = monsterDto.charisma, speedByMovements = monsterDto.speed,
                    skills = skills,
                    savingThrows = savingThrows,
                    damageVulnerabilities = monsterDto.damageVulnerabilities,
                    damageResistances = monsterDto.damageResistances,
                    damageImmunities = monsterDto.damageImmunities,
                    conditionImmunities = monsterDto.conditionImmunities.map { it.name },
                    senses = monsterDto.senses,
                    languages = monsterDto.languages,
                    challenge = Challenge.fromDouble(monsterDto.challengeRating),
                    xp = monsterDto.xp,
                    image = monsterDto.image,
                    specialAbilities = specialAbilities,
                    actions = monsterDto.actions.map { it.toDomain() },
                    legendaryActions = monsterDto.legendaryActions.map { it.toDomain() }
                )
            }
        } catch (e: Exception) {
            Log.e { e.message.toString() }
            return null
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

    companion object {
        val Log = logging("MonsterRepository")
    }
}