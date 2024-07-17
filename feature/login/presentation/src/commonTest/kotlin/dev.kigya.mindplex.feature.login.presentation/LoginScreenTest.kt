package dev.kigya.mindplex.feature.login.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenContent
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class LoginScreenTest {

    @Test
    fun `loginScreen triggers OnFirstLaunch event`() = runComposeUiTest {
        // Given
        var eventTriggered = false

        // When
        setContent {
            MindplexTheme {
                LoginScreenContent(
                    state = LoginContract.State(),
                    event = { event ->
                        if (event == LoginContract.Event.OnFirstLaunch) {
                            eventTriggered = true
                        }
                    }
                )
            }
        }

        // Then
        assertTrue(eventTriggered)
    }

    @Test
    fun `loginScreen displays error stub when stubErrorType is NETWORK`() = runComposeUiTest {
        // Given
        val state = LoginContract.State(stubErrorType = StubErrorType.NETWORK)

        // When
        setContent {
            MindplexTheme {
                LoginScreenContent(
                    state = state,
                    event = {}
                )
            }
        }

        // Then
        onNodeWithTag("error_stub").assertIsDisplayed()
    }

    @Test
    fun `loginScreen triggers OnErrorStubClicked event when error stub is clicked`() = runComposeUiTest {
        // Given
        var eventTriggered = false
        val state = LoginContract.State(stubErrorType = StubErrorType.NETWORK)

        // When
        setContent {
            MindplexTheme {
                LoginScreenContent(
                    state = state,
                    event = { event ->
                        if (event == LoginContract.Event.OnErrorStubClicked) {
                            eventTriggered = true
                        }
                    }
                )
            }
        }

        // Then
        onNodeWithTag("error_stub").performClick()
        assertTrue(eventTriggered)
    }

    @Test
    fun `loginScreen displays login section when stubErrorType is null`() = runComposeUiTest {
        // Given
        val state = LoginContract.State(stubErrorType = null)

        // When
        setContent {
            MindplexTheme {
                LoginScreenContent(
                    state = state,
                    event = {}
                )
            }
        }

        // Then
        onNodeWithTag("login_section").assertIsDisplayed()
    }

    @Test
    fun `loginScreen triggers OnGoogleSignInResultReceived event when Google button is clicked`() = runComposeUiTest {
        // Given
        var eventTriggered = false
        val state = LoginContract.State(stubErrorType = null)

        // When
        setContent {
            MindplexTheme {
                LoginScreenContent(
                    state = state,
                    event = { event ->
                        if (event is LoginContract.Event.OnGoogleSignInResultReceived) {
                            eventTriggered = true
                        }
                    }
                )
            }
        }

        // Then
        onNodeWithTag("google_sign_in_button").performClick()
        assertTrue(eventTriggered)
    }
}
