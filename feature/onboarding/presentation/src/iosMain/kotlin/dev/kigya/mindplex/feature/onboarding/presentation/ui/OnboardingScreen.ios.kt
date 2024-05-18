package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import dev.kigya.mindplex.core.presentation.component.MindplexLottie
import dev.kigya.mindplex.core.presentation.resources.ResourceProvider
import dev.kigya.mindplex.core.util.window.getScreenWidth
import org.jetbrains.compose.resources.DrawableResource

@Composable
actual fun OnboardingLottie(
    modifier: Modifier,
    drawableResource: DrawableResource?,
    lottiePath: String?,
) {
    lottiePath?.let { path ->
        val relativeLottieWidth = getScreenWidth() / LOTTIE_WIDTH_PROPORTIONAL_DIVIDER
        val relativeLottieHeight = relativeLottieWidth * LOTTIE_ASPECT_RATIO
        with(LocalDensity.current) {
            MindplexLottie(
                modifier = Modifier
                    .width(relativeLottieWidth.toDp())
                    .height(relativeLottieHeight.toDp())
                    .then(modifier),
                reader = { with(ResourceProvider.Lottie) { loadAsByteArray(path) } },
            )
        }
    }
}
