package domain.model.character

data class Species(
    val id: Long,
    val fullName: String,
    val cha: Int,
    val con: Int,
    val dex: Int,
    val int: Int,
    val str: Int,
    val wis: Int,
    val special: String
)