package dev.kigya.mindplex.feature.game.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.component.MindplexChip
import dev.kigya.mindplex.core.presentation.component.MindplexFillSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexIconButton
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameIconBack
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameScoreLabel
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameScoreLabelBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameScoreLabelIcon
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameScoreLabelText
import dev.kigya.mindplex.feature.game.presentation.ui.theme.gameTitle
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_arrow_back
import mindplex_multiplatform.feature.game.presentation.generated.resources.ic_game_score_label

@Composable
internal fun GameTopBar(
    modifier: Modifier = Modifier,
    title: String,
    scoreText: String,
    onIconBackClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MindplexTheme.colorScheme.gameBackground),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MindplexIconButton(
            drawableResource = Res.drawable.ic_game_arrow_back,
            tintColor = MindplexTheme.colorScheme.gameIconBack,
            onClick = onIconBackClick,
        )

        MindplexSpacer(size = MindplexTheme.dimension.dp4)

        MindplexText(
            text = title,
            style = MindplexTheme.typography.gameTitle,
            color = MindplexTheme.colorScheme.gameTitle,
        )

        MindplexFillSpacer()

        GameScoreLabel(scoreText = scoreText)
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
                drawableResource = Res.drawable.ic_game_score_label,
                tintColor = MindplexTheme.colorScheme.gameScoreLabelIcon,
            )
        },
        text = scoreText,
        textStyle = MindplexTheme.typography.gameScoreLabel,
        textColor = MindplexTheme.colorScheme.gameScoreLabelText,
        backgroundColor = MindplexTheme.colorScheme.gameScoreLabelBackground,
        isSelected = true,
        isEnabled = false,
        shouldAnimateText = true,
        onClick = { },
    )
}
