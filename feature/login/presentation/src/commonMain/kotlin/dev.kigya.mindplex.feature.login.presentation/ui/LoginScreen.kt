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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import dev.kigya.mindplex.core.presentation.component.MindplexButton
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.core.util.compose.LaunchedEffectSaveable
import dev.kigya.mindplex.core.util.compose.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.util.compose.performClickHapticFeedback
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
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
internal fun LoginScreenContent(state: LoginContract.State, event: (LoginContract.Event) -> Unit) {
    LaunchedEffectSaveable(Unit) { event(LoginContract.Event.OnFirstLaunch) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.spacing.large),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                stubErrorType?.let { type ->
                    MindplexErrorStub(stubErrorType = type) {
                        event(LoginContract.Event.OnErrorStubClicked)
                    }
                } ?: run {
                    LoginSection(event)
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.LoginSection(event: (LoginContract.Event) -> Unit) {
    val hapticFeedback = LocalHapticFeedback.current

    MindplexIcon(
        drawableResource = Res.drawable.ic_mindplex,
        tintColor = MaterialTheme.colorScheme.inverseSurface,
    )
    MindplexSpacer(size = MindplexSpacerSize.LARGE)
    MindplexText(
        text = stringResource(Res.string.login_welcome_to_mindplex),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    MindplexSpacer(size = MindplexSpacerSize.GIANT)
    GoogleButtonUiContainer(
        onGoogleSignInResult = { googleUser ->
            event(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
        },
    ) {
        MindplexButton(
            modifier = Modifier.fillMaxWidth(),
            startIcon = { MindplexIcon(drawableResource = Res.drawable.ic_google) },
            labelText = stringResource(Res.string.login_continue_with_google),
            contentColor = MaterialTheme.colorScheme.surfaceContainer,
            containerColor = MaterialTheme.colorScheme.onSecondary,
            onClick = {
                onClick()
                performClickHapticFeedback(hapticFeedback)
            },
        )
    }
}
