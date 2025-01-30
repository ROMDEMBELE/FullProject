package ui.campaign.search

/**
 * @author Romain Dembele
 *
 * @property id The unique identifier of the campaign
 * @property title The title of the campaign
 */
data class CampaignItem(
    val id: String,
    val title: String,
    val description: String
)