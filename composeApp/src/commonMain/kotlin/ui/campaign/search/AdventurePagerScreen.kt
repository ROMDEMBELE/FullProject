package ui.campaign.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui.campaign.search.composable.AdventureCarouselItem
import ui.campaign.search.composable.CreateAdventureCarouselItem
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventurePagerScreen(
    navHostController: NavHostController,
    viewModel: AdventurePagerViewModel
) {

    val uiState: AdventurePagerUiState by viewModel.uiState.collectAsState()
    val carouselState = rememberCarouselState { uiState.listOfCampaign.size + 1 }
    AnimatedContent(uiState) { state ->
        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = primaryDark
            )
        } else {

            HorizontalUncontainedCarousel(
                state = carouselState,
                modifier = Modifier.fillMaxSize(),
                itemSpacing = 8.dp,
                itemWidth = 300.dp,
                flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(carouselState),
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) { index ->
                if (index >= uiState.listOfCampaign.size) {
                    CreateAdventureCarouselItem {
                        navHostController.navigate("campaign/create")
                    }
                } else {
                    val campaign = uiState.listOfCampaign[index]
                    AdventureCarouselItem(
                        campaign = campaign,
                        onEditClick = {
                            navHostController.navigate("campaign/${campaign.id}/edit")
                        },
                        onDeleteClick = {
                            // TODO show confirmation dialog
                        }
                    )
                }
            }
        }
    }
}