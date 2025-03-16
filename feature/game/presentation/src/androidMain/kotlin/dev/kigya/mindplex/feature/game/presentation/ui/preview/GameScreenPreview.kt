package dev.kigya.mindplex.feature.game.presentation.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.kigya.mindplex.core.presentation.uikit.preview.factory.PreviewScreensFactory
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.GameScreenContent
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_pick_answer_title
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_true_of_false_title

class GameScreenPreviewParameterProvider :
    PreviewParameterProvider<GameContract.State> {

    override val values: Sequence<GameContract.State> = sequenceOf(
        GameContract.State(
            isLoading = true,
            question = null,
            answers = emptyList(),
            currentTime = GameContract.TIME_LIMIT,
            score = 1,
        ),
        GameContract.State(
            isLoading = false,
            question = "What is the capital of France?",
            answers = listOf(
                GameContract.State.Answer("Paris"),
                GameContract.State.Answer("London"),
                GameContract.State.Answer("Berlin"),
                GameContract.State.Answer("Rome"),
            ),
            type = TypePresentationModel.MULTIPLE,
            typeTitle = Res.string.game_modes_pick_answer_title,
            currentTime = 12,
            score = 4,
        ),
        GameContract.State(
            isLoading = false,
            question = "Is Kotlin a statically typed language?",
            answers = listOf(
                GameContract.State.Answer("True"),
                GameContract.State.Answer("False"),
            ),
            type = TypePresentationModel.BOOLEAN,
            typeTitle = Res.string.game_modes_true_of_false_title,
            currentTime = 5,
            score = 1000,
        ),
    )
}

@PreviewScreensFactory
@Composable
private fun GameScreenPreview(
    @PreviewParameter(GameScreenPreviewParameterProvider::class)
    state: GameContract.State,
) {
    GameScreenContent(
        state = state,
        event = Lambda.noOpConsumer(),
    )
}
