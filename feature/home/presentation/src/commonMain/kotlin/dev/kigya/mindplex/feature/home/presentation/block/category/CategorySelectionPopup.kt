package dev.kigya.mindplex.feature.home.presentation.block.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexDialog
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.home.presentation.block.DifficultySectionList
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.categorySelectionPopupBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.categorySelectionTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CategorySelectionPopup(
    state: HomeContract.State.CategorySelectionData,
    modifier: Modifier = Modifier,
    event: (HomeContract.Event) -> Unit,
) {
    MindplexDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = HomeTheme.dimension.dp16.value),
        shouldShowDialog = state.shouldDisplayPopup,
        backgroundColor = HomeTheme.colorScheme.categorySelectionPopupBackground,
        onDismissRequest = { event(HomeContract.Event.OnPopupDismissed) },
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MindplexSpacer(size = HomeTheme.dimension.dp24)

            state.modeTitle?.let { mode ->
                MindplexText(
                    value = stringResource(mode),
                    typography = HomeTheme.typography.categorySelectionTitle,
                    color = HomeTheme.colorScheme.categorySelectionTitle,
                )
            }

            MindplexSpacer(size = HomeTheme.dimension.dp36)

            CategorySelectionPager(
                state = state,
                event = event,
            )

            MindplexSpacer(size = HomeTheme.dimension.dp48)

            DifficultySectionList(
                state = state,
                event = event,
            )

            MindplexSpacer(size = HomeTheme.dimension.dp24)
        }
    }
}
