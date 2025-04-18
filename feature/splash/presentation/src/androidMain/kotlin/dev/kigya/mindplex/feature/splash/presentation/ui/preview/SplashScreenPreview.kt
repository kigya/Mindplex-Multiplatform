package dev.kigya.mindplex.feature.splash.presentation.ui.preview

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.empty
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.uikit.preview.factory.PreviewScreensFactory
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenContent

@PreviewScreensFactory
@Composable
private fun SplashScreenPreview() {
    MindplexTheme {
        SplashScreenContent(
            state = SplashContract.State(shouldDisplayText = true),
            event = Lambda.noOpConsumer(),
            effect = StableFlow.empty(),
        )
    }
}
