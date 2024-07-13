package dev.kigya.mindplex.feature.splash.presentation.ui

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.component.preview.ScreenPreviewFactory
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract

@ScreenPreviewFactory
@Composable
private fun SplashScreenPreview() {
    MindplexTheme {
        SplashScreenContent(
            state = SplashContract.State(
                shouldDisplayText = true,
            ),
            event = { },
        )
    }
}
