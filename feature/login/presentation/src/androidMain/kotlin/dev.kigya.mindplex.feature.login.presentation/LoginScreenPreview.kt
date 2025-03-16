package dev.kigya.mindplex.feature.login.presentation

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.uikit.preview.factory.PreviewScreensFactory
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenContent

@PreviewScreensFactory
@Composable
private fun LoginScreenPreview() {
    MindplexTheme {
        LoginScreenContent(
            state = LoginContract.State(),
            event = Lambda.noOpConsumer(),
        )
    }
}
