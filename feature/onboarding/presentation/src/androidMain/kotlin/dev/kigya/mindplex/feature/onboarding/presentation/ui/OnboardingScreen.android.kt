package dev.kigya.mindplex.feature.onboarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun OnboardingLottie(
    modifier: Modifier,
    drawableResource: DrawableResource?,
    lottiePath: String?,
) {
    drawableResource?.let { resource ->
        Image(
            modifier = modifier,
            painter = painterResource(resource = resource),
            contentDescription = null,
        )
    }
}
