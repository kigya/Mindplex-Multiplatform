package dev.kigya.mindplex.feature.home.presentation.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.util.fastForEach
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.uikit.MindplexChip
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeCategorySelectionButton
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeCategorySelectionDifficultyBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeCategorySelectionDifficultyText
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
            .padding(horizontal = HomeTheme.dimension.dp24.value),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        state.difficulties.fastForEach { difficulty ->
            difficulty.textResource?.let { textResource ->
                MindplexChip(
                    text = stringResource(textResource),
                    textStyle = HomeTheme.typography.homeCategorySelectionButton,
                    isSelected = difficulty.isSelected,
                    textColor = HomeTheme.colorScheme.homeCategorySelectionDifficultyText,
                    backgroundColor = HomeTheme.colorScheme.homeCategorySelectionDifficultyBackground,
                ) {
                    performClickHapticFeedback(haptic)
                    event(HomeContract.Event.OnDifficultyClicked(difficulty))
                }
            }
        }
    }
}
