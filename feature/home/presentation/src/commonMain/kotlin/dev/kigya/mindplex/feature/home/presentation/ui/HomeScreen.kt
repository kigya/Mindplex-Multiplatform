package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.home.presentation.component.CategorySelectionPopup
import dev.kigya.mindplex.feature.home.presentation.component.FactsPager
import dev.kigya.mindplex.feature.home.presentation.component.HomePagerDotsIndicator
import dev.kigya.mindplex.feature.home.presentation.component.HomeScreenHeader
import dev.kigya.mindplex.feature.home.presentation.component.ModesCard
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeBackground

internal expect val PAGER_PLACEHOLDER_WIDTH_DIVIDER: Int
internal expect val MODES_PLACEHOLDER_WIDTH_DIVIDER: Float

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, effect) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
internal fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(HomeContract.Event.OnFirstLaunch) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MindplexTheme.colorScheme.homeBackground)
            .statusBarsPadding()
            .padding(
                horizontal = MindplexTheme.dimension.dp24,
                vertical = MindplexTheme.dimension.dp16,
            ),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("home_section"),
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("error_stub"),
                            stubErrorType = type,
                        ) { event(HomeContract.Event.OnErrorStubClicked) }
                    },
                    ifAbsent = {
                        HomeSection(
                            state = state,
                            event = event,
                            effect = effect,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.HomeSection(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    val pagerState = rememberPagerState(pageCount = state.factsPagerData.facts::size)

    LaunchedEffect(effect) {
        effect.value.collect { homeEffect ->
            when (homeEffect) {
                is HomeContract.Effect.ScrollFactsToNextPage ->
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % pagerState.pageCount,
                        animationSpec = tween(
                            easing = LinearOutSlowInEasing,
                        ),
                    )
            }
        }
    }

    HomeScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        state = state.headerData,
        event = event,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp36)

    FactsPager(
        modifier = Modifier.fillMaxWidth(),
        state = state.factsPagerData,
        pagerState = pagerState,
        facts = state.factsPagerData.facts,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp12)

    HomePagerDotsIndicator(
        modifier = Modifier.fillMaxWidth(),
        state = state.factsPagerData,
        pagerState = pagerState,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp36)

    ModesCard(
        modifier = Modifier.fillMaxWidth(),
        state = state.modesData,
        event = event,
    )

    CategorySelectionPopup(
        state = state.categorySelectionData,
        event = event,
    )
}
