package dev.kigya.mindplex.feature.onboarding.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.util.lerp
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingLottie
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingBackground
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingDescription
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.onboardingTitle
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

private const val LOTTIE_SCALE_LERP_END = 0.5f

@Composable
internal fun ImmutableList<OnboardingContract.State.OnboardingScreenData>.OnboardingPager(
    pagerState: PagerState,
    state: OnboardingContract.State,
) {
    val pageOffset by remember {
        derivedStateOf { abs(pagerState.currentPageOffsetFraction) }
    }
    val scale by remember {
        derivedStateOf { lerp(1f, LOTTIE_SCALE_LERP_END, pageOffset) }
    }

    MindplexHorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(MindplexTheme.colorScheme.onboardingBackground)
            .testTag("onboarding_pager"),
        pagerState = pagerState,
    ) { page ->
        val currentOnboardingScreenData = remember(page) { get(page) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("onboarding_page_$page"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            MindplexSpacer(size = MindplexTheme.dimension.dp12)
            OnboardingLottie(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .testTag("onboarding_lottie_$page"),
                drawableResource = currentOnboardingScreenData.lottieDrawableResource,
                lottiePath = currentOnboardingScreenData.lottiePath,
            )
            MindplexSpacer(size = MindplexTheme.dimension.dp36)
            AnimatedVisibility(visible = state.shouldDisplayTitle) {
                currentOnboardingScreenData.titleTextResource?.let { titleResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MindplexTheme.dimension.dp24)
                            .testTag("onboarding_title_$page"),
                        text = stringResource(titleResource),
                        style = MindplexTheme.typography.onboardingTitle,
                        color = MindplexTheme.colorScheme.onboardingTitle,
                    )
                }
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp8)
            AnimatedVisibility(visible = state.shouldDisplayDescription) {
                currentOnboardingScreenData.descriptionTextResource?.let { descriptionResource ->
                    MindplexText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MindplexTheme.dimension.dp24)
                            .testTag("onboarding_description_$page"),
                        text = stringResource(descriptionResource),
                        style = MindplexTheme.typography.onboardingDescription,
                        color = MindplexTheme.colorScheme.onboardingDescription,
                    )
                }
            }
            MindplexSpacer(size = MindplexTheme.dimension.dp24)
        }
    }
}
