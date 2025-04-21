package dev.kigya.mindplex.feature.game.presentation.block.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexChip
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexIconButton
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
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
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_arrow_back
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_score_label
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GameTopBarContent(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GameTheme.colorScheme.gameBackground.value),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MindplexIconButton(
            resource = Res.drawable.ic_game_arrow_back,
            color = GameTheme.colorScheme.gameIconBack,
            onClick = { event(GameContract.Event.OnBackPressed) },
        )

        MindplexSpacer(size = GameTheme.dimension.dp8)

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            state.typeTitle?.let { textResource ->
                MindplexText(
                    value = stringResource(textResource),
                    color = GameTheme.colorScheme.gameTitle,
                    typography = GameTheme.typography.gameTitle,
                    maxLines = 1,
                )
            }
        }

        MindplexSpacer(size = GameTheme.dimension.dp8)

        GameScoreLabel(scoreText = state.score.toString())
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
