package dev.kigya.mindplex.feature.game.presentation.block.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTitle
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_pick_answer_title
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_score_placeholder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun GameTopBarShimmer(state: GameContract.State) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GameTheme.colorScheme.gameBackground.value)
            .padding(bottom = GameTheme.dimension.dp48.value),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MindplexMeasurablePlaceholder(isLoading = state.isLoading) {
            Box(modifier = Modifier.size(GameTheme.dimension.dp24.value))
        }

        MindplexSpacer(size = GameTheme.dimension.dp8)

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            MindplexMeasurablePlaceholder(
                isLoading = state.isLoading,
                textToMeasure = stringResource(Res.string.game_modes_pick_answer_title),
                textStyle = GameTheme.typography.gameTitle,
            ) {}
        }

        MindplexSpacer(size = GameTheme.dimension.dp8)

        MindplexMeasurablePlaceholder(
            isLoading = state.isLoading,
            textToMeasure = stringResource(Res.string.game_score_placeholder),
            textStyle = GameTheme.typography.gameTitle,
        ) {}
    }
}
