package domain

data class DndCharacter(
    val id: Long? = null,  // ID can be null if it's not assigned yet
    val name: String,
    val age: Int,
    val race: String,
    val characterClass: String,  // Use 'characterClass' instead of 'class' to avoid keyword conflict
    val subclass: String,
    val level: Level,
    val cha: Int,
    val con: Int,
    val dex: Int,
    val int: Int,
    val str: Int,
    val wis: Int,
    val skill: List<String>,
    val features: List<String>  // Use a list to represent features
) {
    fun Int.getModifier(): Int {
        return when (this) {
            in 2..3 -> -4
            in 4..5 -> -3
            in 6..7 -> -2
            in 8..9 -> -1
            in 10..11 -> 0
            in 12..13 -> 1
            in 14..15 -> 2
            in 16..17 -> 3
            in 18..19 -> 4
            in 20..21 -> 5
            else -> throw IllegalArgumentException("Invalid characteristic value")
        }
    }
}