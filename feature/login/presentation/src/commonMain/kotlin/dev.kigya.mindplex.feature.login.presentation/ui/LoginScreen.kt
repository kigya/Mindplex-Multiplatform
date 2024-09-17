package dev.kigya.mindplex.feature.login.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginBackground
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginButton
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginMindplexIcon
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginSignInButtonContainer
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginSignInButtonContent
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginTitle
import mindplex_multiplatform.feature.login.presentation.generated.resources.Res
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_google
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_mindplex
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_continue_with_google
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_welcome_to_mindplex
import org.jetbrains.compose.resources.stringResource

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
    LaunchedEffectSaveable(Unit) { event(LoginContract.Event.OnFirstLaunch) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MindplexTheme.colorScheme.loginBackground)
            .padding(horizontal = MindplexTheme.dimension.dp24),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("login_section"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier.testTag("error_stub"),
                            stubErrorType = type,
                        ) { event(LoginContract.Event.OnErrorStubClicked) }
                    },
                    ifAbsent = {
                        LoginSection(event)
                    },
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.LoginSection(event: (LoginContract.Event) -> Unit) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexIcon(
        drawableResource = Res.drawable.ic_mindplex,
        tintColor = MindplexTheme.colorScheme.loginMindplexIcon,
    )
    MindplexSpacer(size = MindplexTheme.dimension.dp24)
    MindplexText(
        text = stringResource(Res.string.login_welcome_to_mindplex),
        style = MindplexTheme.typography.loginTitle,
        color = MindplexTheme.colorScheme.loginTitle,
    )
    MindplexSpacer(size = MindplexTheme.dimension.dp64)
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
            textStyle = MindplexTheme.typography.loginButton,
            contentColor = MindplexTheme.colorScheme.loginSignInButtonContent,
            containerColor = MindplexTheme.colorScheme.loginSignInButtonContainer,
            onClick = {
                onClick()
                performClickHapticFeedback(hapticFeedback)
            },
        )
    }
}
