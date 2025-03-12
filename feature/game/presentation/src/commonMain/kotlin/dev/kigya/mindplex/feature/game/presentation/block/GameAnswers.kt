package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import dev.kigya.mindplex.core.presentation.common.util.MindplexAdaptiveContainer
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameInactiveOptionBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameInactiveOptionBorder
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameInactiveOptionText
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameRightOptionBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameRightOptionBorder
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameRightOptionText
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameWrongOptionBackground
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameWrongOptionBorder
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme.gameWrongOptionText
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel
import kotlinx.coroutines.delay

private const val DISPLAY_DELAY = 100L

@Composable
internal fun rememberAnswerBackgroundColor(
    displayType: GameContract.State.Answer.DisplayType,
): MindplexDsToken<Color> {
    val color = when (displayType) {
        GameContract.State.Answer.DisplayType.CORRECT -> GameTheme.colorScheme.gameRightOptionBackground
        GameContract.State.Answer.DisplayType.INCORRECT -> GameTheme.colorScheme.gameWrongOptionBackground
        GameContract.State.Answer.DisplayType.NEUTRAL -> GameTheme.colorScheme.gameInactiveOptionBackground
    }

    return remember(displayType) { color }
}

@Composable
internal fun rememberAnswerBorderColor(
    displayType: GameContract.State.Answer.DisplayType,
): MindplexDsToken<Color> {
    val color = when (displayType) {
        GameContract.State.Answer.DisplayType.CORRECT -> GameTheme.colorScheme.gameRightOptionBorder
        GameContract.State.Answer.DisplayType.INCORRECT -> GameTheme.colorScheme.gameWrongOptionBorder
        GameContract.State.Answer.DisplayType.NEUTRAL -> GameTheme.colorScheme.gameInactiveOptionBorder
    }

    return remember(displayType) { color }
}

@Composable
internal fun rememberAnswerTextColor(
    displayType: GameContract.State.Answer.DisplayType,
): MindplexDsToken<Color> {
    val color = when (displayType) {
        GameContract.State.Answer.DisplayType.CORRECT -> GameTheme.colorScheme.gameRightOptionText
        GameContract.State.Answer.DisplayType.INCORRECT -> GameTheme.colorScheme.gameWrongOptionText
        GameContract.State.Answer.DisplayType.NEUTRAL -> GameTheme.colorScheme.gameInactiveOptionText
    }

    return remember(displayType) { color }
}

@Composable
internal fun rememberShakeEvent(displayType: GameContract.State.Answer.DisplayType): ShakeEvent {
    val shakeEvent = when (displayType) {
        GameContract.State.Answer.DisplayType.CORRECT -> ShakeEvent.POSITIVE
        GameContract.State.Answer.DisplayType.INCORRECT -> ShakeEvent.NEGATIVE
        GameContract.State.Answer.DisplayType.NEUTRAL -> ShakeEvent.NONE
    }

    return remember(displayType) { shakeEvent }
}

@Composable
internal fun GameAnswers(
    state: GameContract.State,
    onAnswerClick: (answerIndex: Int) -> Unit,
) {
    MindplexAdaptiveContainer(
        portrait = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = GameTheme.dimension.dp24.value),
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnswerList(
                    state = state,
                    onAnswerClick = onAnswerClick,
                    isLandscape = false,
                )
            }
        },
        landscape = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = GameTheme.dimension.dp24.value),
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnswerList(
                    state = state,
                    onAnswerClick = onAnswerClick,
                    isLandscape = true,
                )
            }
        },
    )
}

@Composable
private fun AnswerList(
    state: GameContract.State,
    onAnswerClick: (answerIndex: Int) -> Unit,
    isLandscape: Boolean,
) {
    if (state.type == TypePresentationModel.BOOLEAN) {
        BooleanAnswerList(
            state = state,
            onAnswerClick = onAnswerClick,
        )
    } else {
        MultipleAnswerList(
            state = state,
            onAnswerClick = onAnswerClick,
            isLandscape = isLandscape,
        )
    }
}

@Composable
private fun BooleanAnswerList(
    state: GameContract.State,
    onAnswerClick: (answerIndex: Int) -> Unit,
) {
    val animatedIndexes = remember { mutableStateListOf<Boolean>() }
    val previousQuestion = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.question, state.isLoading) {
        if (!state.isLoading && !state.question.isNullOrEmpty() && state.question != previousQuestion.value) {
            previousQuestion.value = state.question
            animatedIndexes.clear()
            repeat(state.answers.size) { animatedIndexes.add(false) }

            state.answers.indices.forEach { index ->
                delay(DISPLAY_DELAY * index)
                animatedIndexes[index] = true
            }
        }
    }

    if (state.answers.isEmpty()) return

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(GameTheme.dimension.dp8.value),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        state.answers.forEachIndexed { index, answer ->
            AnimatedVisibility(
                visible = animatedIndexes.getOrElse(index) { false },
                modifier = Modifier.weight(1f),
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight / 2 },
                ) + fadeIn(animationSpec = tween()),
                exit = fadeOut(animationSpec = tween()),
            ) {
                key(answer.text) {
                    AnswerContent(
                        answer = answer,
                        index = index,
                        isValidating = state.isValidatingAnswer,
                        modifier = Modifier.fillMaxWidth(),
                        onAnswerClick = onAnswerClick,
                    )
                }
            }
        }
    }
}

@Composable
@Suppress("MagicNumber")
private fun MultipleAnswerList(
    state: GameContract.State,
    onAnswerClick: (answerIndex: Int) -> Unit,
    isLandscape: Boolean,
) {
    val alphaList = remember { mutableStateListOf<Animatable<Float, AnimationVector1D>>() }
    val previousQuestion = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.question, state.isLoading) {
        if (!state.isLoading && !state.question.isNullOrEmpty() && state.question != previousQuestion.value) {
            previousQuestion.value = state.question

            alphaList.clear()
            state.answers.forEach {
                alphaList.add(Animatable(0f))
            }

            state.answers.indices.forEach { index ->
                delay(DISPLAY_DELAY * index)
                alphaList[index].animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f),
                    ),
                )
            }
        }
    }

    if (state.answers.isEmpty()) return

    if (isLandscape) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(GameTheme.dimension.dp16.value),
            maxItemsInEachRow = 2,
        ) {
            state.answers.forEachIndexed { index, answer ->
                val alpha = alphaList.getOrNull(index)?.value ?: 0f

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.48f)
                        .graphicsLayer {
                            this.alpha = alpha
                        },
                ) {
                    AnswerContent(
                        answer = answer,
                        index = index,
                        isValidating = state.isValidatingAnswer,
                        modifier = Modifier.fillMaxWidth(),
                        onAnswerClick = onAnswerClick,
                    )
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween()),
            verticalArrangement = Arrangement.spacedBy(GameTheme.dimension.dp16.value),
        ) {
            itemsIndexed(
                items = state.answers,
                key = { _, answer -> answer.text.toString() },
            ) { index, answer ->
                val alpha = alphaList.getOrNull(index)?.value ?: 0f

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            this.alpha = alpha
                        },
                ) {
                    AnswerContent(
                        answer = answer,
                        index = index,
                        isValidating = state.isValidatingAnswer,
                        modifier = Modifier.fillMaxWidth(),
                        onAnswerClick = onAnswerClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun AnswerContent(
    answer: GameContract.State.Answer,
    isValidating: Boolean,
    index: Int,
    modifier: Modifier = Modifier,
    onAnswerClick: (Int) -> Unit,
) {
    val backgroundColor = rememberAnswerBackgroundColor(answer.displayType)
    val borderColor = rememberAnswerBorderColor(answer.displayType)
    val textColor = rememberAnswerTextColor(answer.displayType)
    val shakeEvent = rememberShakeEvent(answer.displayType)

    answer.text?.let {
        GameAnswer(
            text = answer.text,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            textColor = textColor,
            shakeEvent = shakeEvent,
            isValidating = isValidating,
            modifier = modifier,
        ) { onAnswerClick(index) }
    }
}
