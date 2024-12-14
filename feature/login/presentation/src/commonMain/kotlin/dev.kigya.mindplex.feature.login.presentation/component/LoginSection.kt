package dev.kigya.mindplex.feature.login.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginButton
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginMindplexIcon
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginSignInButtonContainer
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginSignInButtonContent
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginTitle
import mindplex_multiplatform.feature.login.presentation.generated.resources.Res
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_google
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_mindplex
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_continue_with_google
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_welcome_to_mindplex
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.LoginSection(event: (LoginContract.Event) -> Unit) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexIcon(
        drawableResource = Res.drawable.ic_mindplex,
        tintColor = LoginTheme.colorScheme.loginMindplexIcon,
    )
    MindplexSpacer(size = LoginTheme.dimension.dp24)
    MindplexText(
        text = stringResource(Res.string.login_welcome_to_mindplex),
        style = LoginTheme.typography.loginTitle,
        color = LoginTheme.colorScheme.loginTitle,
    )
    MindplexSpacer(size = LoginTheme.dimension.dp64)
    GoogleButtonUiContainer(
        onGoogleSignInResult = { googleUser ->
            event(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
        },
    ) {
        MindplexButton(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("google_sign_in_button"),
            startIcon = { MindplexIcon(drawableResource = Res.drawable.ic_google) },
            labelText = stringResource(Res.string.login_continue_with_google),
            textStyle = LoginTheme.typography.loginButton,
            contentColor = LoginTheme.colorScheme.loginSignInButtonContent,
            containerColor = LoginTheme.colorScheme.loginSignInButtonContainer,
            onClick = {
                onClick()
                performClickHapticFeedback(hapticFeedback)
            },
        )
    }
}
