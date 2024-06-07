package domain

data class Monster(
    val index: String,
    val name: String,
    val isFavorite: Boolean = false,
    val challenge: Challenge,
    val size: CreatureSize? = null,
    val type: CreatureType? = null,
    val alignment: Alignment? = null,
    val armorClass: String? = null,
    val hitPoints: Int? = null,
    val hitPointsRoll: String? = null,
    val walkSpeed: Int? = null,
    val swimSpeed: Int? = null,
    val flySpeed: Int? = null,
    val strength: Int? = null,
    val dexterity: Int? = null,
    val constitution: Int? = null,
    val intelligence: Int? = null,
    val wisdom: Int? = null,
    val charisma: Int? = null,
    val proficiencies: List<String> = emptyList(),
    val damageVulnerabilities: List<String> = emptyList(),
    val damageResistances: List<String> = emptyList(),
    val damageImmunities: List<String> = emptyList(),
    val conditionImmunities: List<String> = emptyList(),
    val senses: List<String> = emptyList(),
    val passivePerception: Int? = null,
    val languages: String? = null,
    val proficiencyBonus: Int? = null,
    val xp: Int? = null,
    val specialAbilities: List<SpecialAbility> = emptyList(),
    val actions: List<Action> = emptyList(),
    val image: String? = null,
    val legendaryActions: List<Action> = emptyList()
) {
    data class SpecialAbility(
        val name: String,
        val desc: String
    )

    data class Action(
        val name: String,
        val desc: String,
        val damageType: String,
        val damageDice: String
    )
}

