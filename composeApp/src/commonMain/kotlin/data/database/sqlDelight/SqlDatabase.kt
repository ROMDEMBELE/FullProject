package data.database.sqlDelight

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import domain.model.Condition
import domain.model.DamageType
import domain.model.Environment
import domain.model.Feat
import domain.model.Level
import domain.model.character.Skill
import domain.model.magicItem.MagicItem
import domain.model.monster.Action
import domain.model.monster.Challenge
import domain.model.monster.Monster
import domain.model.monster.Trait
import domain.model.spell.Spell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.dembeyo.data.BackgroundDbo
import org.dembeyo.data.CampaingnDbo
import org.dembeyo.data.CharacterDbo
import org.dembeyo.data.FeatDbo
import org.dembeyo.data.MagicItemDbo
import org.dembeyo.data.MonsterDbo
import org.dembeyo.data.MySqlDelightDatabase
import org.dembeyo.data.RaceDbo
import org.dembeyo.data.SpellDbo

class SqlDatabase(driverFactory: DriverFactory) {

    /**
     * Adapter for [List<Skill>]
     */
    private val listOfSkillAdapter = object : ColumnAdapter<List<Skill>, String> {
        override fun decode(databaseValue: String): List<Skill> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split(",").mapNotNull { Skill.fromId(it) }
            }

        override fun encode(value: List<Skill>): String = value.joinToString(",")
    }

    /**
     * Adapter for [List<String>]
     */
    private val listOfStringAdapter = object : ColumnAdapter<List<String>, String> {
        override fun decode(databaseValue: String): List<String> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split(",")
            }

        override fun encode(value: List<String>): String = value.joinToString(",")
    }

    /**
     * Adapter for [List<Action>]
     */
    private val listOfActionAdapter = object : ColumnAdapter<List<Action>, String> {
        override fun decode(databaseValue: String): List<Action> = if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split("|").mapNotNull { Json.decodeFromString(it) }
        }

        override fun encode(value: List<Action>): String =
            value.joinToString("|") { Json.encodeToString(it) }
    }

    /**
     * Adapter for [Challenge]
     */
    private val challengeAdapter = object : ColumnAdapter<Challenge, Double> {
        override fun decode(databaseValue: Double): Challenge =
            Challenge.fromRating(databaseValue)

        override fun encode(value: Challenge): Double = value.rating
    }

    /**
     * Adapter for [List<Trait>]
     */
    private val listOfTraitAdapter = object : ColumnAdapter<List<Trait>, String> {
        override fun decode(databaseValue: String): List<Trait> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split("|").mapNotNull { Json.decodeFromString(it) }
            }

        override fun encode(value: List<Trait>): String {
            return value.joinToString("|") { Json.encodeToString(it) }
        }
    }

    /**
     * Adapter for [List<DamageType>]
     */
    private val listOfDamageTypeAdapter = object : ColumnAdapter<List<DamageType>, String> {
        override fun decode(databaseValue: String): List<DamageType> =
            if (databaseValue.isEmpty()) {
                listOf()
            } else {
                databaseValue.split("|").map { DamageType.valueOf(it) }
            }

        override fun encode(value: List<DamageType>): String = value.joinToString("|")
    }

    /**
     * Adapter for [List<Condition>]
     */
    private val listOfConditionAdapter = object : ColumnAdapter<List<Condition>, String> {
        override fun decode(databaseValue: String): List<Condition> =
            if (databaseValue.isEmpty()) {
                emptyList()
            } else {
                databaseValue.split(",").map { Condition.valueOf(it) }
            }

        override fun encode(value: List<Condition>): String {
            return value.joinToString(",")
        }
    }

    /**
     * Adapter for [List<Environment>]
     */
    private val listOfEnvironmentAdapter = object : ColumnAdapter<List<Environment>, String> {
        override fun decode(databaseValue: String): List<Environment> =
            if (databaseValue.isEmpty()) {
                emptyList()
            } else {
                databaseValue.split(",").map { Environment.valueOf(it) }
            }

        override fun encode(value: List<Environment>): String {
            return value.joinToString(",")
        }
    }

    private val listOfSpellOptionAdapter = object : ColumnAdapter<List<Spell.SpellOption>, String> {
        override fun decode(databaseValue: String): List<Spell.SpellOption> =
            if (databaseValue.isEmpty()) {
                emptyList()
            } else {
                databaseValue.split("|").map { Json.decodeFromString(it) }
            }

        override fun encode(value: List<Spell.SpellOption>): String {
            return value.joinToString("|") { Json.encodeToString(it) }
        }
    }

    private val database = MySqlDelightDatabase(
        driver = driverFactory.createDriver(),
        CharacterDboAdapter = CharacterDbo.Adapter(
            levelAdapter = EnumColumnAdapter(),
        ),
        BackgroundDboAdapter = BackgroundDbo.Adapter(
            skillsAdapter = listOfSkillAdapter,
        ),
        MonsterDboAdapter = MonsterDbo.Adapter(
            challengeAdapter = challengeAdapter,
            sizeAdapter = EnumColumnAdapter(),
            typeAdapter = EnumColumnAdapter(),
            alignmentAdapter = EnumColumnAdapter(),
            condition_immunitiesAdapter = listOfConditionAdapter,
            damage_immunitiesAdapter = listOfDamageTypeAdapter,
            damage_resistancesAdapter = listOfDamageTypeAdapter,
            damage_vulnerabilitiesAdapter = listOfDamageTypeAdapter,
            environmentsAdapter = listOfEnvironmentAdapter,
            languagesAdapter = listOfStringAdapter,
            actionsAdapter = listOfActionAdapter,
            reactionsAdapter = listOfActionAdapter,
            legendary_actionsAdapter = listOfActionAdapter,
            bonus_actionsAdapter = listOfActionAdapter,
            traitsAdapter = listOfTraitAdapter
        ),
        SpellDboAdapter = SpellDbo.Adapter(
            levelAdapter = EnumColumnAdapter(),
            schoolAdapter = EnumColumnAdapter(),
            saving_throw_abilityAdapter = EnumColumnAdapter(),
            casting_optionsAdapter = listOfSpellOptionAdapter,
            damage_typeAdapter = listOfDamageTypeAdapter
        ),
        FeatDboAdapter = FeatDbo.Adapter(
            benefitsAdapter = listOfStringAdapter
        ),
        MagicItemDboAdapter = MagicItemDbo.Adapter(
            categoryAdapter = EnumColumnAdapter(),
            rarityAdapter = EnumColumnAdapter()
        )
    )

    fun getAllMonsters(): Flow<List<MonsterDbo>> =
        database.monsterQueries.getAll().asFlow().mapToList(Dispatchers.IO)

    fun getMonsterById(id: String): MonsterDbo? =
        database.monsterQueries.getByKey(id).executeAsOneOrNull()

    fun deleteMonsterById(id: String) = database.monsterQueries.deleteByKey(id)

    fun insertOrUpdateMonster(monster: Monster) {
        database.monsterQueries.insertOrReplace(
            key = monster.key,
            name = monster.name,
            challenge = monster.challenge,
            size = monster.size,
            type = monster.type,
            alignment = monster.alignment,
            armors_class = monster.armorsClass.toLong(),
            hit_points = monster.hitPoints.toLong(),
            charisma = monster.charisma.toLong(),
            charisma_save = monster.charismaSave?.toLong(),
            dexterity = monster.dexterity.toLong(),
            dexterity_save = monster.dexteritySave?.toLong(),
            constitution = monster.constitution.toLong(),
            constitution_save = monster.constitutionSave?.toLong(),
            intelligence = monster.intelligence.toLong(),
            intelligence_save = monster.intelligenceSave?.toLong(),
            strength = monster.strength.toLong(),
            strength_save = monster.strengthSave?.toLong(),
            wisdom = monster.wisdom.toLong(),
            wisdom_save = monster.wisdomSave?.toLong(),
            walk_speed = monster.walkSpeed,
            swim_speed = monster.swimSpeed,
            fly_speed = monster.flySpeed,
            climb_speed = monster.climbSpeed,
            burrow_speed = monster.burrowSpeed,
            hover = monster.hover,
            arcana = monster.arcana?.toLong(),
            acrobatics = monster.acrobatics?.toLong(),
            animal_handling = monster.animalHandling?.toLong(),
            deception = monster.deception?.toLong(),
            history = monster.history?.toLong(),
            insight = monster.insight?.toLong(),
            intimidation = monster.intimidation?.toLong(),
            investigation = monster.investigation?.toLong(),
            medicine = monster.medicine?.toLong(),
            nature = monster.nature?.toLong(),
            perception = monster.perception?.toLong(),
            performance = monster.performance?.toLong(),
            persuasion = monster.persuasion?.toLong(),
            religion = monster.religion?.toLong(),
            sleight_of_hand = monster.sleightOfHand?.toLong(),
            stealth = monster.stealth?.toLong(),
            survival = monster.survival?.toLong(),
            non_magical_attack_resistance = monster.nonMagicalAttackResistance,
            non_magical_attack_immunity = monster.nonMagicalAttackImmunity,
            damage_vulnerabilities = monster.damageVulnerabilities,
            damage_resistances = monster.damageResistances,
            damage_immunities = monster.damageImmunities,
            condition_immunities = monster.conditionImmunities,
            passive_perception = monster.passivePerception.toLong(),
            sight_range = monster.sightRange,
            dark_vision_range = monster.darkVisionRange,
            blind_sight_range = monster.blindSightRange,
            tremor_sense_range = monster.tremorSenseRange,
            true_sight_range = monster.trueSightRange,
            languages = monster.languages,
            traits = monster.traits,
            actions = monster.actions,
            bonus_actions = monster.bonusActions,
            reactions = monster.reactions,
            legendary_actions = monster.legendaryActions,
            environments = monster.environments
        )
    }

    fun getAllSpells(): Flow<List<SpellDbo>> =
        database.spellQueries.getAll().asFlow().mapToList(Dispatchers.IO)

    fun getSpellById(id: String): SpellDbo? =
        database.spellQueries.getByKey(id).executeAsOneOrNull()

    fun deleteSpellById(id: String) = database.spellQueries.deleteByKey(id)

    fun insertOrUpdateSpell(spell: Spell) {
        database.spellQueries.insertOrReplace(
            key = spell.key,
            name = spell.name,
            level = spell.level,
            description = spell.description,
            higher_level = spell.higherLevel,
            school = spell.school,
            range = spell.range,
            verbal = spell.verbal,
            somatic = spell.somatic,
            material = spell.material,
            cost = spell.cost,
            ritual = spell.ritual,
            duration = spell.duration,
            concentration = spell.concentration,
            casting_time = spell.castingTime,
            saving_throw_ability = spell.savingThrowAbility,
            attack_roll = spell.attackRoll,
            damage_type = spell.damageType,
            target_count = spell.targetCount?.toLong(),
            casting_options = spell.castingOptions
        )
    }

    fun getAllFeat(): Flow<List<FeatDbo>> =
        database.featQueries.getAll().asFlow().mapToList(Dispatchers.IO)

    fun getFeatById(id: String): FeatDbo? =
        database.featQueries.getByKey(id).executeAsOneOrNull()

    fun insertOrUpdateFeat(feat: Feat) {
        database.featQueries.insertOrReplace(
            key = feat.key,
            hasPrerequisite = feat.hasPrerequisites,
            prerequisite = feat.prerequisite,
            name = feat.name,
            benefits = feat.benefits
        )
    }

    fun getAllMagicItem(): Flow<List<MagicItemDbo>> =
        database.magicItemQueries.getAll().asFlow().mapToList(Dispatchers.IO)

    fun getMagicItemById(id: String): MagicItemDbo? =
        database.magicItemQueries.getByKey(id).executeAsOneOrNull()

    fun insertOrUpdateMagicItem(magicItem: MagicItem) {
        database.magicItemQueries.insertOrReplace(
            key = magicItem.key,
            name = magicItem.name,
            category = magicItem.category,
            rarity = magicItem.rarity,
            description = magicItem.description,
            cost = magicItem.cost,
            weight = magicItem.weight,
            is_magical = magicItem.isMagical,
            require_attunement = magicItem.requireAttunement
        )
    }

    fun deleteMagicItemById(id: String) = database.magicItemQueries.deleteByKey(id)

// section Character

    fun getAllCharacter(): Flow<List<CharacterDbo>> =
        database.characterQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getCharacterById(id: Long): Flow<CharacterDbo?> =
        database.characterQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun deleteCharacterById(id: Long) = database.characterQueries.deleteById(id)

    fun insertOrUpdateCharacter(
        id: Long?,
        fullName: String,
        player: String,
        campaignId: Long,
        speciesId: Long,
        backgroundId: Long,
        level: Level,
        _class: String,
        armor: Long,
        life: Long,
        spellSave: Long,
        cha: Long,
        con: Long,
        dex: Long,
        int: Long,
        str: Long,
        wis: Long
    ): Long? {
        database.characterQueries.insertOrUpdate(
            id,
            fullName,
            player,
            campaignId,
            speciesId,
            level,
            _class,
            backgroundId,
            armor,
            life,
            spellSave,
            cha,
            con,
            dex,
            int,
            str,
            wis
        )
        return database.characterQueries.lastInsertRowId().executeAsOneOrNull()
    }

    // Section Campaign
    fun getAllCampaign(): Flow<List<CampaingnDbo>> =
        database.campaignQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getCampaignById(id: Long): Flow<CampaingnDbo?> =
        database.campaignQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    fun insertOrUpdateCampaign(
        id: Long?,
        title: String,
        description: String,
    ): Long? {
        database.campaignQueries.insertOrUpdate(id, title, description)
        return database.campaignQueries.lastInsertRowId().executeAsOneOrNull()
    }

    fun deleteCampaignById(id: Long) = database.campaignQueries.deleteById(id)

    // section Race
    fun getAllRace(): Flow<List<RaceDbo>> =
        database.raceQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getRaceById(id: Long): Flow<RaceDbo?> =
        database.raceQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)

    // section Background
    fun getAllBackground(): Flow<List<BackgroundDbo>> =
        database.backgroundQueries.selectAll().asFlow().mapToList(Dispatchers.IO)

    fun getBackgroundById(id: Long): Flow<BackgroundDbo?> =
        database.backgroundQueries.selectById(id).asFlow().mapToOne(Dispatchers.IO)
}