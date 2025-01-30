package ui.campaign.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui.campaign.search.composable.CampaignPageItem
import ui.campaign.search.composable.CreateCampaignButtonItem
import ui.composable.CustomAnimatedPlaceHolder
import ui.composable.darkGray
import ui.composable.lightGray
import ui.composable.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignListScreen(
    navHostController: NavHostController,
    viewModel: CampaignListViewModel
) {

    val uiState: CampaignListUiState by viewModel.uiState.collectAsState()
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { uiState.listOfCampaign.size + 1 })
    AnimatedContent(uiState) { state ->
        if (state.isLoading) {
            CustomAnimatedPlaceHolder(
                backgroundColor = Color.Transparent,
                contentColor = primaryDark
            )
        } else {
            Box {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 16.dp,
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                ) { page ->
                    if (page >= uiState.listOfCampaign.size) {
                        CreateCampaignButtonItem {
                            navHostController.navigate("campaign/create")
                        }
                    } else {
                        val campaign = uiState.listOfCampaign[page]
                        CampaignPageItem(
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
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) darkGray else lightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}