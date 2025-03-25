package dev.kigya.mindplex.feature.onboarding.presentation.block

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.util.lerp
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexLottie
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.onboarding.presentation.ui.provider.OnboardingAdaptiveMetrics.LocalOnboardingLottieWidthRatio
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.Res
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.im_onboarding_first
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.math.abs

private const val ONBOARDING_LOTTIE_ASPECT_RATIO = 2.24f

@Composable
internal fun OnboardingLottie(
    lottiePath: String?,
    pagerState: PagerState,
    orientation: Orientation,
) {
    val pageOffset by remember {
        derivedStateOf { abs(pagerState.currentPageOffsetFraction) }
    }

    val scale by remember {
        derivedStateOf {
            lerp(
                start = 1f,
                stop = 0.5f,
                fraction = pageOffset,
            )
        }
    }

    if (orientation == Orientation.Vertical) {
        drawOnboardingLottieContent(
            lottiePath = lottiePath,
            scale = scale,
            pagerState = pagerState,
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            drawOnboardingLottieContent(
                lottiePath = lottiePath,
                scale = scale,
                pagerState = pagerState,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMindplexUiKitApi::class)
@Composable
private fun drawOnboardingLottieContent(
    lottiePath: String?,
    scale: Float,
    pagerState: PagerState,
) {
    if (LocalInspectionMode.current) {
        MindplexIcon(
            resource = Res.drawable.im_onboarding_first,
            modifier = Modifier
                .fillMaxWidth(1f / LocalOnboardingLottieWidthRatio.current)
                .aspectRatio(1f / ONBOARDING_LOTTIE_ASPECT_RATIO),
        )
    } else {
        lottiePath?.let { path ->
            MindplexLottie(
                reader = { Res.readBytes(lottiePath) },
                modifier = Modifier
                    .fillMaxWidth(1f / LocalOnboardingLottieWidthRatio.current)
                    .aspectRatio(1f / ONBOARDING_LOTTIE_ASPECT_RATIO)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .testTag("onboarding_lottie_${pagerState.currentPage}"),
            )
        }
    }
}
