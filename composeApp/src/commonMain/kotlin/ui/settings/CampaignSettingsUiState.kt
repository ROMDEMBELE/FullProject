package ui.settings

data class CampaignSettingsUiState(
    val id: Long? = null,
    val fullName: String? = null,
    val description: String? = null,
    val progress: Float? = null,
) {

    val hasCampaignIsProgress: Boolean
        get() = id != null
}
