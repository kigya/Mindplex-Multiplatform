package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextAnimation
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameQuestion
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_question_placeholder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMindplexUiKitApi::class)
@Composable
internal fun GameQuestion(
    state: GameContract.State,
    modifier: Modifier = Modifier,
) = MindplexMeasurablePlaceholder(
    isLoading = state.isLoading,
    textToMeasure = stringResource(Res.string.game_question_placeholder),
    textStyle = GameTheme.typography.gameQuestion,
    modifier = modifier,
) {
    state.question?.let { text ->
        MindplexText(
            value = text,
            color = GameTheme.colorScheme.gameQuestion,
            typography = GameTheme.typography.gameQuestion,
            animation = MindplexTextAnimation.Typewriter,
        )
    }
}
