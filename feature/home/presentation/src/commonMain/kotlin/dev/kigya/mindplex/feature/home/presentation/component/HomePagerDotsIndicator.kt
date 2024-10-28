package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeFactsPagerDotsIndicator

@Composable
internal fun ColumnScope.HomePagerDotsIndicator(
    modifier: Modifier = Modifier,
    state: HomeContract.State.FactsPagerData,
    pagerState: PagerState,
) {
    MindplexSpacer(size = MindplexTheme.dimension.dp24)

    AnimatedVisibility(
        visible = state.areFactsLoading.not(),
    ) {
        Row(
            modifier = modifier.testTag("home_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(
                pagerState = pagerState,
                selectedColor = MindplexTheme.colorScheme.homeFactsPagerDotsIndicator,
                unselectedColor = MindplexTheme.colorScheme.homeFactsPagerDotsIndicator,
            )
            MindplexSpacer(size = MindplexTheme.dimension.dp64)
        }
    }
}
