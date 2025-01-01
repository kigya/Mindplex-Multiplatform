package dev.kigya.mindplex.feature.login.presentation.block

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.uikit.MindplexButton
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.provider.LoginAdaptiveMetrics.LocalLoginButtonModifier
import dev.kigya.mindplex.feature.login.presentation.ui.provider.LoginAdaptiveMetrics.LocalLoginButtonsHorizontalPadding
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginButton
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginButtonBackground
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginButtonText
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginSignInButtonBorder
import mindplex_multiplatform.feature.login.presentation.generated.resources.Res
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_google
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_continue_with_google
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginButton(event: (LoginContract.Event) -> Unit) = if (LocalInspectionMode.current) {
    LoginButtonContent(Lambda.empty())
} else {
    GoogleButtonUiContainer(
        onGoogleSignInResult = { googleUser ->
            event(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
        },
    ) {
        LoginButtonContent(::onClick)
    }
}

@Composable
private fun LoginButtonContent(onClick: () -> Unit) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexButton(
        text = stringResource(Res.string.login_continue_with_google),
        backgroundColor = LoginTheme.colorScheme.loginButtonBackground,
        borderColor = LoginTheme.colorScheme.loginSignInButtonBorder,
        textColor = LoginTheme.colorScheme.loginButtonText,
        modifier = LocalLoginButtonModifier.current.testTag("google_sign_in_button"),
        textTypography = LoginTheme.typography.loginButton,
        horizontalPadding = LocalLoginButtonsHorizontalPadding.current,
        leadingIcon = {
            MindplexIcon(resource = Res.drawable.ic_google)
        },
        onClick = {
            onClick()
            performClickHapticFeedback(hapticFeedback)
        },
    )
}
