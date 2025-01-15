package ui.feat.search

data class SearchFeatItem(
    val key: String,
    val name: String,
    val benefits: List<String>,
    val hasPrerequisites: Boolean,
    val prerequisites: String? = null,
)