package dev.kigya.mindplex.feature.game.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.game.presentation.component.GameTimer
import dev.kigya.mindplex.feature.game.presentation.component.GameTopBar
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(contract: GameContract) {
    val (state, event, effect) = use(contract)

    GameScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
internal fun GameScreenContent(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
    effect: StableFlow<GameContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(GameContract.Event.OnFirstLaunch) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GameTheme.colorScheme.gameBackground)
            .statusBarsPadding()
            .padding(
                horizontal = GameTheme.dimension.dp24,
                vertical = GameTheme.dimension.dp16,
            ),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("game_section"),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("error_stub"),
                            stubErrorType = type,
                        ) { event(GameContract.Event.OnErrorStubClicked) }
                    },
                    ifAbsent = {
                        HomeSection(
                            state = state,
                            event = event,
                            effect = effect,
                        )
                    },
                )
            }
        }
    }
}

@Suppress("UnusedParameter")
@Composable
private fun ColumnScope.HomeSection(
    state: GameContract.State,
    event: (GameContract.Event) -> Unit,
    effect: StableFlow<GameContract.Effect>,
) {
    val textToDisplay = remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(1)
            textToDisplay.value = 1
        }
    }

    GameTopBar(
        modifier = Modifier.fillMaxWidth(),
        title = "True or false Mastery",
        scoreText = textToDisplay.value.toString(),
        onIconBackClick = { },
    )

    MindplexSpacer(size = GameTheme.dimension.dp64)

    GameTimer(
        remainingTime = 20,
        initialTime = 60,
    )

    MindplexSpacer(size = GameTheme.dimension.dp64)

    MindplexText(
        text = "Silhouette — a song performed by the group “KANA-BOON” is featured as the sixteenth opening " +
            "of which anime?",
        color = GameTheme.colorScheme.gameQuestion,
        style = GameTheme.typography.gameQuestion,
    )
}
