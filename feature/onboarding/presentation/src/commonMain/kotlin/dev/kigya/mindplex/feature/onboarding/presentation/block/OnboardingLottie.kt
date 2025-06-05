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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.util.lerp
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexLottieComposition
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.onboarding.presentation.ui.provider.OnboardingAdaptiveMetrics.LocalOnboardingLottieWidthRatio
import io.github.alexzhirkevich.compottie.LottieComposition
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
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
    val lottiePaths = listOf(
        "files/onboarding_first.json",
        "files/onboarding_second.json",
        "files/onboarding_third.json",
    )

    val preloadedCompositions = preloadLottieCompositions(lottiePaths)
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
            preloadedCompositions = preloadedCompositions,
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
                preloadedCompositions = preloadedCompositions,
            )
        }
    }
}

@Composable
private fun drawOnboardingLottieContent(
    preloadedCompositions: Map<String, LottieComposition?>,
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
        preloadedCompositions[lottiePath]?.let { composition ->
            MindplexLottieComposition(
                composition = composition,
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun preloadLottieCompositions(lottiePaths: List<String>): Map<String, LottieComposition?> {
    val jsonMap by produceState<Map<String, String?>>(initialValue = emptyMap()) {
        val loaded = mutableMapOf<String, String?>()
        for (path in lottiePaths) {
            loaded[path] = Res.readBytes(path).decodeToString()
        }
        value = loaded
    }

    return jsonMap.mapValues { (_, json) ->
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(json ?: String.empty)
        }
        composition
    }
}
