package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.util.fastForEach
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.component.MindplexChip
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.categorySelectionButton
import dev.kigya.mindplex.feature.home.presentation.ui.theme.categorySelectionDifficultyBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.categorySelectionDifficultyText
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DifficultySectionList(
    modifier: Modifier = Modifier,
    state: HomeContract.State.CategorySelectionData,
    event: (HomeContract.Event) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MindplexTheme.dimension.dp24),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        state.difficulties.fastForEach { difficulty ->
            difficulty.textResource?.let { textResource ->
                MindplexChip(
                    text = stringResource(textResource),
                    textStyle = MindplexTheme.typography.categorySelectionButton,
                    isSelected = difficulty.isSelected,
                    textColor = MindplexTheme.colorScheme.categorySelectionDifficultyText,
                    backgroundColor = MindplexTheme.colorScheme.categorySelectionDifficultyBackground,
                ) {
                    performClickHapticFeedback(haptic)
                    event(HomeContract.Event.OnDifficultyClicked(difficulty))
                }
            }
        }
    }
}
