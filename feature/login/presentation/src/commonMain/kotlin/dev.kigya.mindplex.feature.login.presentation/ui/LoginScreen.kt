package dev.kigya.mindplex.feature.login.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.login.presentation.component.LoginSection
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.theme.loginBackground

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
