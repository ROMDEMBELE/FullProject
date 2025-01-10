package domain.model

data class Feat(
    val key: String,
    val hasPrerequisites: Boolean = false,
    val prerequisite: String? = null,
    val name: String,
    val benefits: List<String>,
)
