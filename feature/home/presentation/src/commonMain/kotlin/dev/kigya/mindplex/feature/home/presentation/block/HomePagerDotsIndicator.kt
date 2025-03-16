package dev.kigya.mindplex.feature.home.presentation.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexDotsIndicator
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerDotsIndicator

@Composable
internal fun ColumnScope.HomePagerDotsIndicator(
    modifier: Modifier = Modifier,
    state: HomeContract.State.FactsData,
    pagerState: PagerState,
) {
    MindplexSpacer(size = HomeTheme.dimension.dp24)

    AnimatedVisibility(
        visible = state.areFactsLoading.not(),
    ) {
        Row(
            modifier = modifier.testTag("home_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexDotsIndicator(
                pagerState = pagerState,
                color = HomeTheme.colorScheme.homeFactsPagerDotsIndicator,
                orientation = Orientation.Horizontal,
            )
            MindplexSpacer(size = HomeTheme.dimension.dp64)
        }
    }
}
