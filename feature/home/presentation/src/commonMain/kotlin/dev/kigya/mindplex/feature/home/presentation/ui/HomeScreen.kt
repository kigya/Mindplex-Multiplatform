package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.kigya.mindplex.core.presentation.common.extension.listenToLifecycleEvents
import dev.kigya.mindplex.core.presentation.common.util.MindplexAdaptiveContainer
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexErrorStubContainer
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.home.presentation.block.FactsSection
import dev.kigya.mindplex.feature.home.presentation.block.HomeLandscapeContainer
import dev.kigya.mindplex.feature.home.presentation.block.HomeScreenHeader
import dev.kigya.mindplex.feature.home.presentation.block.ModesCard
import dev.kigya.mindplex.feature.home.presentation.block.category.CategorySelectionPopup
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.provider.HomeCompositionLocalProvider
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeBackground

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, _) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
) = HomeCompositionLocalProvider {
    MindplexErrorStubContainer(
        background = HomeTheme.colorScheme.homeBackground,
        stubErrorType = state.stubErrorType,
        verticalArrangement = Arrangement.Top,
        onRetryButtonClicked = { event(HomeContract.Event.OnErrorStubClicked) },
    ) {
        LocalLifecycleOwner.current.listenToLifecycleEvents()

        MindplexAdaptiveContainer(
            portrait = {
                HomePortraitSection(
                    state = state,
                    event = event,
                )
            },
            landscape = {
                HomeLandscapeSection(
                    state = state,
                    event = event,
                )
            },
        )
    }
}

@Composable
private fun ColumnScope.HomePortraitSection(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
) {
    HomeScreenHeader(
        state = state.headerData,
        modifier = Modifier.fillMaxWidth(),
        event = event,
    )

    MindplexSpacer(size = HomeTheme.dimension.dp36)

    FactsSection(state = state.factsData)

    MindplexSpacer(size = HomeTheme.dimension.dp36)

    ModesCard(
        state = state.modesData,
        event = event,
    )

    CategorySelectionPopup(
        state = state.categorySelectionData,
        event = event,
    )
}

@Composable
private fun ColumnScope.HomeLandscapeSection(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
) {
    HomeLandscapeContainer {
        HomeScreenHeader(
            state = state.headerData,
            modifier = Modifier.fillMaxWidth(),
            event = event,
        )

        MindplexSpacer(size = HomeTheme.dimension.dp36)

        FactsSection(state = state.factsData)

        MindplexSpacer(size = HomeTheme.dimension.dp36)

        ModesCard(
            state = state.modesData,
            event = event,
        )

        CategorySelectionPopup(
            state = state.categorySelectionData,
            event = event,
        )
    }
}
