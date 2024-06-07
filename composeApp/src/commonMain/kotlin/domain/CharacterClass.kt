package domain

data class CharacterClass(
    val index: String,
    val name: String,
) {
    override fun toString(): String = name
}