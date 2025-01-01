package dev.kigya.mindplex.feature.login.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexErrorStubContainer
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.login.presentation.block.LoginButton
import dev.kigya.mindplex.feature.login.presentation.block.LoginIcon
import dev.kigya.mindplex.feature.login.presentation.block.LoginText
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.provider.LoginAdaptiveMetrics.LocalLoginTextSpacing
import dev.kigya.mindplex.feature.login.presentation.ui.provider.LoginCompositionLocalProvider
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginBackground

@Composable
fun LoginScreen(contract: LoginContract) {
    val (state, event, _) = use(contract)

    LoginScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun LoginScreenContent(
    state: LoginContract.State,
    event: (LoginContract.Event) -> Unit,
) {
    LoginCompositionLocalProvider {
        MindplexErrorStubContainer(
            background = LoginTheme.colorScheme.loginBackground,
            stubErrorType = state.stubErrorType,
            modifier = Modifier.testTag("login_section"),
            onRetryButtonClicked = { event(LoginContract.Event.OnErrorStubClicked) },
        ) {
            LoginIcon()
            MindplexSpacer(size = LoginTheme.dimension.dp24)

            LoginText()
            MindplexSpacer(size = LocalLoginTextSpacing.current)

            LoginButton(event)
        }
    }
}
