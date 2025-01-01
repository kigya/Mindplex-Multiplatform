package dev.kigya.mindplex.feature.home.presentation.block.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.util.dsl.invokeIfPresent
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import kotlinx.collections.immutable.ImmutableList

private const val CHUNKED_CATEGORIES_GRID = 2

@Composable
internal fun CategorySelectionPage(
    modifier: Modifier = Modifier,
    items: ImmutableList<HomeContract.State.CategorySelectionData.CategoryData>,
    event: (HomeContract.Event) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(CHUNKED_CATEGORIES_GRID),
        horizontalArrangement = Arrangement.spacedBy(HomeTheme.dimension.dp16.value),
        verticalArrangement = Arrangement.spacedBy(HomeTheme.dimension.dp16.value),
    ) {
        items.forEachIndexed { index, categoryData ->
            item {
                invokeIfPresent(
                    p1 = items[index].icon,
                    p2 = items[index].text,
                ) { iconResource, textResource ->
                    CategoryGridItem(
                        icon = iconResource,
                        name = textResource,
                    ) {
                        performClickHapticFeedback(haptic)
                        event(HomeContract.Event.OnCategoryClicked(categoryData))
                    }
                }
            }
        }
    }
}
