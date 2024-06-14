package domain

enum class Skill(val id: String, val displayName: String, val abilityScore: Ability) {
    DECEPTION("deception", "Deception", Ability.CHA),
    INTIMIDATION("intimidation", "Intimidation", Ability.CHA),
    PERFORMANCE("performance", "Performance", Ability.CHA),
    PERSUASION("persuasion", "Persuasion", Ability.CHA),
    ACROBATICS("acrobatics", "Acrobatics", Ability.DEX),
    SLEIGHT_OF_HAND("sleight_of_hand", "Sleight of Hand", Ability.DEX),
    STEALTH("stealth", "Stealth", Ability.DEX),
    ARCANA("arcana", "Arcana", Ability.INT),
    HISTORY("history", "History", Ability.INT),
    INVESTIGATION("investigation", "Investigation", Ability.INT),
    NATURE("nature", "Nature", Ability.INT),
    RELIGION("religion", "Religion", Ability.INT),
    ATHLETICS("athletics", "Athletics", Ability.STR),
    ANIMAL_HANDLING("animal_handling", "Animal Handling", Ability.WIS),
    INSIGHT("insight", "Insight", Ability.WIS),
    MEDICINE("medicine", "Medicine", Ability.WIS),
    PERCEPTION("perception", "Perception", Ability.WIS),
    SURVIVAL("survival", "Survival", Ability.WIS);

    companion object {
        fun fromId(id: String): Skill? {
            return entries.find { it.id == id }
        }
    }
}