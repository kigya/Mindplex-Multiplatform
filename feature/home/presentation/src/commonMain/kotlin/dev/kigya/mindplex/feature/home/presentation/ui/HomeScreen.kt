package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.common.extension.ShiftClickButtonState
import dev.kigya.mindplex.core.presentation.common.extension.shiftClickEffect
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexScaleIcon
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.component.MindplexTypewriterText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeFactsPagerBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeFactsPagerDescription
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeFactsPagerDotsIndicator
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeFactsPagerTitle
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesCardArrow
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesCardBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesCardDescription
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesCardTitle
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesDelimiter
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeModesIconBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeProfileNameText
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeWelcomeBackText
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_facts_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_mode_arrow
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.home.presentation.generated.resources.im_facts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val HOME_FACTS_PAGER_WEIGHT = 0.5f
private const val HOME_MODES_ICON_INITIAL_SCALE = 1f
private const val HOME_MODES_ICON_TARGET_SCALE = 1.5f
internal expect val PAGER_PLACEHOLDER_WIDTH_DIVIDER: Int
internal expect val MODES_PLACEHOLDER_WIDTH_DIVIDER: Float

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, effect) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
internal fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(HomeContract.Event.OnFirstLaunch) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MindplexTheme.colorScheme.homeBackground)
            .statusBarsPadding()
            .padding(
                horizontal = MindplexTheme.dimension.dp24,
                vertical = MindplexTheme.dimension.dp16,
            ),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("home_section"),
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("error_stub"),
                            stubErrorType = type,
                        ) { event(HomeContract.Event.OnErrorStubClicked) }
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

@Composable
private fun ColumnScope.HomeSection(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    val pagerState = rememberPagerState(pageCount = state.pagerData.facts::size)

    LaunchedEffect(effect) {
        effect.value.collect { homeEffect ->
            when (homeEffect) {
                is HomeContract.Effect.ScrollToNextPage ->
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % pagerState.pageCount,
                        animationSpec = tween(
                            easing = LinearOutSlowInEasing,
                        ),
                    )
            }
        }
    }

    HomeScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        state = state.headerData,
        event = event,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp36)

    FactsPager(
        modifier = Modifier.fillMaxWidth(),
        state = state.pagerData,
        pagerState = pagerState,
        facts = state.pagerData.facts,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp12)

    HomePagerDotsIndicator(
        modifier = Modifier.fillMaxWidth(),
        state = state.pagerData,
        pagerState = pagerState,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp36)

    ModesCard(
        modifier = Modifier.fillMaxWidth(),
        state = state.modesData,
        event = event,
    )
}

@Composable
private fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    state: HomeContract.State.HeaderData,
    event: (HomeContract.Event) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            MindplexPlaceholder(isLoading = state.isProfileNameLoading) {
                MindplexText(
                    text = stringResource(Res.string.home_welcome_back),
                    color = MindplexTheme.colorScheme.homeWelcomeBackText,
                    style = MindplexTheme.typography.homeWelcomeBackText,
                )
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp8)
            MindplexPlaceholder(
                isLoading = state.isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MindplexTheme.typography.homeProfileNameText,
            ) {
                MindplexTypewriterText(text = state.userName)
            }
        }
        MindplexPlaceholder(isLoading = state.isProfilePictureLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(MindplexTheme.dimension.dp48)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = null,
                error = painterResource(Res.drawable.ic_profile_fallback),
                onError = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
            )
        }
    }
}

@Composable
private fun FactsPager(
    modifier: Modifier = Modifier,
    state: HomeContract.State.PagerData,
    pagerState: PagerState,
    facts: ImmutableList<String>,
) {
    val screenWidth = LocalWindow.current.width.toFloat()

    MindplexPlaceholder(
        modifier = modifier,
        size = Size(
            width = screenWidth,
            height = screenWidth / PAGER_PLACEHOLDER_WIDTH_DIVIDER,
        ),
        isLoading = state.areFactsLoading,
    ) {
        MindplexHorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_pager"),
            pagerState = pagerState,
            pageSpacing = MindplexTheme.dimension.dp16,
        ) { page ->
            val currentFact = remember(page) { facts[page] }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(MindplexTheme.shape.rounding16)
                    .background(MindplexTheme.colorScheme.homeFactsPagerBackground)
                    .padding(MindplexTheme.dimension.dp16)
                    .testTag("facts_page_$page"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MindplexIcon(drawableResource = Res.drawable.im_facts)

                MindplexSpacer(size = MindplexTheme.dimension.dp16)

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    MindplexText(
                        modifier = Modifier.weight(HOME_FACTS_PAGER_WEIGHT),
                        text = stringResource(Res.string.home_facts_title),
                        style = MindplexTheme.typography.homeFactsPagerTitle,
                        color = MindplexTheme.colorScheme.homeFactsPagerTitle,
                    )
                    MindplexText(
                        modifier = Modifier.weight(1f),
                        text = currentFact,
                        textAlign = TextAlign.Start,
                        style = MindplexTheme.typography.homeFactsPagerDescription,
                        color = MindplexTheme.colorScheme.homeFactsPagerDescription,
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.HomePagerDotsIndicator(
    modifier: Modifier = Modifier,
    state: HomeContract.State.PagerData,
    pagerState: PagerState,
) {
    MindplexSpacer(size = MindplexTheme.dimension.dp24)
    AnimatedVisibility(
        visible = state.areFactsLoading.not(),
    ) {
        Row(
            modifier = modifier.testTag("home_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(
                pagerState = pagerState,
                selectedColor = MindplexTheme.colorScheme.homeFactsPagerDotsIndicator,
                unselectedColor = MindplexTheme.colorScheme.homeFactsPagerDotsIndicator,
            )
            MindplexSpacer(size = MindplexTheme.dimension.dp64)
        }
    }
}

@Composable
private fun ModesCard(
    modifier: Modifier = Modifier,
    state: HomeContract.State.ModesData,
    event: (HomeContract.Event) -> Unit,
) {
    val screenWidth = LocalWindow.current.width.dp
    val screenHeight = LocalWindow.current.height.toFloat()

    MindplexPlaceholder(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        isLoading = state.areModesLoading,
        size = Size(
            width = screenWidth.value,
            height = screenHeight / MODES_PLACEHOLDER_WIDTH_DIVIDER,
        ),
    ) {
        Card(
            modifier = modifier,
            shape = MindplexTheme.shape.rounding16,
            colors = CardDefaults.cardColors(
                containerColor = MindplexTheme.colorScheme.homeModesDelimiter,
            ),
        ) {
            state.modes.fastForEachIndexed { index, mode ->
                if (state.areModesLoading.not()) {
                    mode.icon?.let { iconRes ->
                        Surface(
                            modifier = Modifier.shiftClickEffect(
                                onChangeState = { buttonState ->
                                    event(
                                        HomeContract.Event.OnModeClickStateChanged(
                                            index = index,
                                            shouldScaleIcon = buttonState == ShiftClickButtonState.Pressed,
                                        ),
                                    )
                                },
                                onClick = { event(HomeContract.Event.OnModeClicked(mode.type)) },
                            ),
                            color = MindplexTheme.colorScheme.homeModesCardBackground,
                        ) {
                            Row(
                                modifier = Modifier.padding(all = MindplexTheme.dimension.dp16),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(MindplexTheme.shape.rounding8)
                                        .background(MindplexTheme.colorScheme.homeModesIconBackground),
                                    contentAlignment = Alignment.BottomCenter,
                                ) {
                                    MindplexIcon(
                                        drawableResource = iconRes,
                                    )
                                }

                                MindplexSpacer(size = MindplexTheme.dimension.dp16)

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    mode.title?.let { titleRes ->
                                        MindplexText(
                                            text = stringResource(titleRes),
                                            style = MindplexTheme.typography.homeModesCardTitle,
                                            color = MindplexTheme.colorScheme.homeModesCardTitle,
                                            textAlign = TextAlign.Start,
                                        )
                                    }

                                    MindplexSpacer(size = MindplexTheme.dimension.dp12)

                                    mode.description?.let { descriptionRes ->
                                        MindplexText(
                                            text = stringResource(descriptionRes),
                                            style = MindplexTheme.typography.homeModesCardDescription,
                                            color = MindplexTheme.colorScheme.homeModesCardDescription,
                                            textAlign = TextAlign.Start,
                                        )
                                    }
                                }

                                MindplexScaleIcon(
                                    scale = if (mode.shouldScaleIcon) {
                                        HOME_MODES_ICON_TARGET_SCALE
                                    } else {
                                        HOME_MODES_ICON_INITIAL_SCALE
                                    },
                                    drawableResource = Res.drawable.ic_mode_arrow,
                                    tintColor = MindplexTheme.colorScheme.homeModesCardArrow,
                                )
                            }
                        }
                        if (mode.shouldDisplayDelimiter) {
                            MindplexSpacer(
                                modifier = Modifier.background(MindplexTheme.colorScheme.homeModesDelimiter),
                                size = MindplexTheme.dimension.dp1,
                            )
                        }
                    }
                }
            }
        }
    }
}
