package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexJumpingDotsIndicator
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
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
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeProfileNameText
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeWelcomeBackText
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_facts_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.home.presentation.generated.resources.im_facts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val HOME_FACTS_PAGER_WEIGHT = 0.5f

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, _) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
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
) {
    val pagerState = rememberPagerState(pageCount = state.facts::size)

    HomeScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        event = event,
        name = state.userName,
        avatarUrl = state.avatarUrl,
        isProfileNameLoading = state.isProfileNameLoading,
        isProfilePictureLoading = state.isProfilePictureLoading,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp36)

    state.facts.FactsPager(
        state = state,
        pagerState = pagerState,
    )

    MindplexSpacer(size = MindplexTheme.dimension.dp12)

    HomePagerDotsIndicator(
        state = state,
        pagerState = pagerState,
    )
}

@Composable
private fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    event: (HomeContract.Event) -> Unit,
    name: String,
    avatarUrl: String?,
    isProfileNameLoading: Boolean,
    isProfilePictureLoading: Boolean,
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
            MindplexPlaceholder(isLoading = isProfileNameLoading) {
                MindplexText(
                    text = stringResource(Res.string.home_welcome_back),
                    color = MindplexTheme.colorScheme.homeWelcomeBackText,
                    style = MindplexTheme.typography.homeWelcomeBackText,
                )
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp8)
            MindplexPlaceholder(
                isLoading = isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MindplexTheme.typography.homeProfileNameText,
            ) {
                MindplexTypewriterText(text = name)
            }
        }
        MindplexPlaceholder(isLoading = isProfilePictureLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(MindplexTheme.dimension.dp48)
                    .clip(CircleShape),
                model = avatarUrl,
                contentDescription = null,
                error = painterResource(Res.drawable.ic_profile_fallback),
                onError = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
            )
        }
    }
}

@Composable
private fun ImmutableList<String>.FactsPager(
    state: HomeContract.State,
    pagerState: PagerState,
) {
    val screenWidth = LocalWindow.current.width.toFloat()

    MindplexPlaceholder(
        modifier = Modifier.fillMaxWidth(),
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
            val currentFact = remember(page) { get(page) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(MindplexTheme.shape.rounding16)
                    .background(MindplexTheme.colorScheme.homeFactsPagerBackground)
                    .padding(MindplexTheme.dimension.dp16)
                    .testTag("facts_page_$page"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
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
    state: HomeContract.State,
    pagerState: PagerState,
) {
    MindplexSpacer(size = MindplexTheme.dimension.dp24)
    AnimatedVisibility(visible = state.areFactsLoading.not()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_dots_indicator"),
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

internal expect val PAGER_PLACEHOLDER_WIDTH_DIVIDER: Int
