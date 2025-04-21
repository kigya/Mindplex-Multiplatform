package dev.kigya.mindplex.feature.game.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexErrorStubContainer
import dev.kigya.mindplex.feature.game.presentation.block.GameAnswers
import dev.kigya.mindplex.feature.game.presentation.block.GameContainer
import dev.kigya.mindplex.feature.game.presentation.block.GameQuestion
import dev.kigya.mindplex.feature.game.presentation.block.GameTimer
import dev.kigya.mindplex.feature.game.presentation.block.topbar.GameTopBar
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameBackground
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.CategoryPresentationModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.DifficultyPresentationModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel

@Composable
fun GameScreen(
    contract: GameContract,
    type: TypePresentationModel,
    category: CategoryPresentationModel? = null,
    difficulty: DifficultyPresentationModel? = null,
) {
    val (state, event, _) = use(contract)

    LaunchedEffectSaveable(Unit) {
        event(
            GameContract.Event.OnFirstLaunch(
                type = type,
                category = category,
                difficulty = difficulty,
            ),
        )
    }

    GameScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun GameScreenContent(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
) {
    MindplexErrorStubContainer(
        background = GameTheme.colorScheme.gameBackground,
        stubErrorType = state.stubErrorType,
        verticalArrangement = Arrangement.Top,
        onRetryButtonClicked = { event(GameContract.Event.OnErrorStubClicked) },
    ) {
        GameContainer {
            GameSection(
                state = state,
                event = event,
            )
        }
    }
}

@Composable
private fun ColumnScope.GameSection(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
) {
    GameTopBar(
        state = state,
        event = event,
    )

    GameTimer(state = state)

    GameQuestion(state = state)

    GameAnswers(
        state = state,
    ) { event(GameContract.Event.OnQuestionAnswered(it)) }
}
