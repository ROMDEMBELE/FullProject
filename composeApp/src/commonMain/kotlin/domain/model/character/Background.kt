package domain.model.character

data class Background(
    val id: Long,
    val name: String,
    val feature: String,
    val skills: List<Skill>
)