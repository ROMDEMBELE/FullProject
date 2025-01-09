package domain.model.character

import domain.model.Ability

enum class Skill(
    val id: String,
    val abilityScore: Ability
) {
    DECEPTION("deception", Ability.CHA),
    INTIMIDATION("intimidation", Ability.CHA),
    PERFORMANCE("performance", Ability.CHA),
    PERSUASION("persuasion", Ability.CHA),
    ACROBATICS("acrobatics", Ability.DEX),
    SLEIGHT_OF_HAND("sleight_of_hand", Ability.DEX),
    STEALTH("stealth", Ability.DEX),
    ARCANA("arcana", Ability.INT),
    HISTORY("history", Ability.INT),
    INVESTIGATION("investigation", Ability.INT),
    NATURE("nature", Ability.INT),
    RELIGION("religion", Ability.INT),
    ATHLETICS("athletics", Ability.STR),
    ANIMAL_HANDLING("animal_handling", Ability.WIS),
    INSIGHT("insight", Ability.WIS),
    MEDICINE("medicine", Ability.WIS),
    PERCEPTION("perception", Ability.WIS),
    SURVIVAL("survival", Ability.WIS);

    companion object {
        fun fromId(id: String): Skill? = entries.find { it.id == id }
    }
}