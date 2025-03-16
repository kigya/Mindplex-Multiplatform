package dev.kigya.mindplex.feature.splash.presentation.block

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexLottie
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.provider.SplashAdaptiveMetrics.LocalSplashLogoWidthRatio
import mindplex_multiplatform.feature.splash.presentation.generated.resources.Res
import mindplex_multiplatform.feature.splash.presentation.generated.resources.mindplex_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SplashLogo(event: (SplashContract.Event) -> Unit) {
    val testTagModifier = Modifier.testTag("splash_lottie")

    if (LocalInspectionMode.current) {
        MindplexIcon(
            resource = Res.drawable.mindplex_logo,
            modifier = Modifier
                .fillMaxWidth(1f / LocalSplashLogoWidthRatio.current)
                .aspectRatio(1f)
                .then(testTagModifier),
        )
    } else {
        MindplexLottie(
            reader = { Res.readBytes("files/mindplex_logo.json") },
            modifier = Modifier
                .fillMaxWidth(1f / LocalSplashLogoWidthRatio.current)
                .aspectRatio(1f)
                .then(testTagModifier),
            speed = 1.5f,
            onFinish = { event(SplashContract.Event.OnAnimationFinished) },
        )
    }
}
