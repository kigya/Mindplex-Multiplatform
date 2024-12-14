package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.component.MindplexCircleIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.util.extension.getPagingData
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme

private const val CIRCLE_INDICATOR_WEIGHT = 0.5f
private const val HOME_CATEGORIES_ITEMS_PER_PAGE = 4

@Composable
internal fun ColumnScope.CategorySelectionPager(
    modifier: Modifier = Modifier,
    state: HomeContract.State.CategorySelectionData,
    event: (HomeContract.Event) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { state.categories.size / 4 })

    MindplexHorizontalPager(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag("category_pager"),
        pagerState = pagerState,
        pageSpacing = HomeTheme.dimension.dp16,
        beyondViewportPageCount = pagerState.pageCount,
    ) { page ->
        val currentCategories = remember(page) {
            state.categories.getPagingData(
                page = page,
                itemsPerPage = HOME_CATEGORIES_ITEMS_PER_PAGE,
            )
        }
        CategorySelectionPage(
            items = currentCategories,
            event = event,
        )
    }

    MindplexSpacer(size = HomeTheme.dimension.dp8)

    MindplexCircleIndicator(
        modifier = Modifier.fillMaxWidth(CIRCLE_INDICATOR_WEIGHT),
        pagerState = pagerState,
    )
}
