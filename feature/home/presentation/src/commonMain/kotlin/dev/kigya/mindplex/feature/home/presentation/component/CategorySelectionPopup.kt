package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.component.MindplexDialog
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.categorySelectionPopupBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.categorySelectionTitle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CategorySelectionPopup(
    modifier: Modifier = Modifier,
    state: HomeContract.State.CategorySelectionData,
    event: (HomeContract.Event) -> Unit,
) {
    MindplexDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MindplexTheme.dimension.dp16),
        shouldShowDialog = state.shouldDisplayPopup,
        backgroundColor = MindplexTheme.colorScheme.categorySelectionPopupBackground,
        onDismissRequest = { event(HomeContract.Event.OnPopupDismissed) },
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MindplexSpacer(size = MindplexTheme.dimension.dp24)

            state.modeTitle?.let { mode ->
                MindplexText(
                    text = stringResource(mode),
                    style = MindplexTheme.typography.categorySelectionTitle,
                    color = MindplexTheme.colorScheme.categorySelectionTitle,
                )
            }

            MindplexSpacer(size = MindplexTheme.dimension.dp36)

            CategorySelectionPager(
                state = state,
                event = event,
            )

            MindplexSpacer(size = MindplexTheme.dimension.dp48)

            DifficultySectionList(
                state = state,
                event = event,
            )

            MindplexSpacer(size = MindplexTheme.dimension.dp24)
        }
    }
}
