package domain

data class Character(
    val id: Long? = null,  // ID can be null if it's not assigned yet
    val name: String,
    val age: Int,
    val race: String,
    val characterClass: CharacterClass,  // Use 'characterClass' instead of 'class' to avoid keyword conflict
    val subclass: String,
    val level: Level,
    val abilities: Map<Ability, Int>,
    val skill: List<Skill>,
    val features: List<Feature>  // Use a list to represent features
) {

}