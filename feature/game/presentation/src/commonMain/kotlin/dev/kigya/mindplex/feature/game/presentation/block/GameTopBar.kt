package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexChip
import dev.kigya.mindplex.core.presentation.uikit.MindplexFillSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexIconButton
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameIconBack
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameScoreLabel
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameScoreLabelBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameScoreLabelIcon
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameScoreLabelText
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameTitle
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_pick_answer_title
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_arrow_back
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_score_label
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun GameTopBar(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
) {
    AnimatedVisibility(
        visible = !state.isLoading,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
        ) + fadeOut(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GameTheme.colorScheme.gameBackground.value),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MindplexIconButton(
                resource = Res.drawable.ic_game_arrow_back,
                color = GameTheme.colorScheme.gameIconBack,
                onClick = { event(GameContract.Event.OnBackPressed) },
            )

            MindplexSpacer(size = GameTheme.dimension.dp4)

            MindplexMeasurablePlaceholder(
                isLoading = state.isLoading,
                textToMeasure = stringResource(Res.string.game_modes_pick_answer_title),
                textStyle = GameTheme.typography.gameTitle,
            ) {
                state.typeTitle?.let { textResource ->
                    MindplexText(
                        value = stringResource(textResource),
                        color = GameTheme.colorScheme.gameTitle,
                        typography = GameTheme.typography.gameTitle,
                    )
                }
            }

            MindplexFillSpacer()

            MindplexMeasurablePlaceholder(state.isLoading) {
                GameScoreLabel(scoreText = state.score.toString())
            }
        }
    }
}

@Composable
private fun GameScoreLabel(
    modifier: Modifier = Modifier,
    scoreText: String,
) {
    MindplexChip(
        modifier = modifier,
        leadingIcon = {
            MindplexIcon(
                resource = Res.drawable.ic_game_score_label,
                color = GameTheme.colorScheme.gameScoreLabelIcon,
            )
        },
        text = scoreText,
        textStyle = GameTheme.typography.gameScoreLabel,
        textColor = GameTheme.colorScheme.gameScoreLabelText,
        backgroundColor = GameTheme.colorScheme.gameScoreLabelBackground,
        isSelected = true,
        isEnabled = false,
        shouldAnimateText = true,
        onClick = { },
    )
}
