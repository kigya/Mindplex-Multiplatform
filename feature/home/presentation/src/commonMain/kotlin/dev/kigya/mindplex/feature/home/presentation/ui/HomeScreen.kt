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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.component.MindplexTypewriterText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_facts_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import mindplex_multiplatform.feature.home.presentation.generated.resources.im_facts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
internal fun HomeScreenContent(state: HomeContract.State, event: (HomeContract.Event) -> Unit) {
    LaunchedEffectSaveable(Unit) { event(HomeContract.Event.OnFirstLaunch) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.medium,
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

    MindplexSpacer(size = MindplexSpacerSize.EXTRA_LARGE)

    state.facts.FactsPager(
        state = state,
        pagerState = pagerState,
    )

    MindplexSpacer(size = MindplexSpacerSize.SMALL)

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
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            MindplexSpacer(size = MindplexSpacerSize.EXTRA_SMALL)
            MindplexPlaceholder(
                isLoading = isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MaterialTheme.typography.headlineMedium,
            ) {
                MindplexTypewriterText(text = name)
            }
        }
        MindplexPlaceholder(isLoading = isProfilePictureLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(MaterialTheme.spacing.extraExtraLarge)
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
private fun ImmutableList<String>.FactsPager(state: HomeContract.State, pagerState: PagerState) {
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
            pageSpacing = MaterialTheme.spacing.medium,
        ) { page ->
            val currentFact = remember(page) { get(page) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.medium))
                    .background(MaterialTheme.colorScheme.outline)
                    .padding(all = MaterialTheme.spacing.medium)
                    .testTag("facts_page_$page"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MindplexIcon(drawableResource = Res.drawable.im_facts)

                MindplexSpacer(size = MindplexSpacerSize.MEDIUM)

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    @Suppress("MagicNumber")
                    MindplexText(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(Res.string.home_facts_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                    MindplexText(
                        modifier = Modifier.weight(1f),
                        text = currentFact,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.HomePagerDotsIndicator(state: HomeContract.State, pagerState: PagerState) {
    MindplexSpacer(size = MindplexSpacerSize.LARGE)
    AnimatedVisibility(visible = state.areFactsLoading.not()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_dots_indicator"),
            horizontalArrangement = Arrangement.Center,
        ) {
            MindplexJumpingDotsIndicator(
                pagerState = pagerState,
                selectedColor = MaterialTheme.colorScheme.primaryContainer,
                unselectedColor = MaterialTheme.colorScheme.primaryContainer,
            )
            MindplexSpacer(size = MindplexSpacerSize.GIANT)
        }
    }
}

internal expect val PAGER_PLACEHOLDER_WIDTH_DIVIDER: Int
