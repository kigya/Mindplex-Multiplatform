package dev.kigya.mindplex.feature.splash.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenContent
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class SplashScreenTest {

    @Test
    fun `splashScreen displays initial state`() = runComposeUiTest {
        // Given & When
        setContent {
            SplashScreenContent(
                state = SplashContract.State(),
                event = {},
            )
        }

        // Then
        onNodeWithTag("splash_lottie").assertIsDisplayed()
        onNodeWithText("splash_text").assertDoesNotExist()
    }

    @Test
    fun `splashScreen displays text when shouldDisplayText is true`() = runComposeUiTest {
        // Given & When
        setContent {
            SplashScreenContent(
                state = SplashContract.State(shouldDisplayText = true),
                event = { },
            )
        }

        // Then
        onNodeWithTag("splash_lottie").assertIsDisplayed()
        onNodeWithTag("splash_text").assertIsDisplayed()
    }

    @Test
    fun `splashScreen displays text when animation finished`() = runComposeUiTest {
        // Given
        var shouldDisplayText = false

        // When
        setContent {
            SplashScreenContent(
                state = SplashContract.State(shouldDisplayText = true),
                event = { event ->
                    if (event == SplashContract.Event.OnAnimationFinished) {
                        shouldDisplayText = true
                    }
                },
            )
        }

        mainClock.advanceTimeUntil { shouldDisplayText }

        // Then
        onNodeWithTag("splash_lottie").assertIsDisplayed()
        onNodeWithTag("splash_text").assertIsDisplayed()
    }

    @Test
    fun `splashScreen triggers OnFirstLaunch event`() = runComposeUiTest {
        // Given
        var eventTriggered = false

        // When
        setContent {
            SplashScreenContent(
                state = SplashContract.State(),
                event = { event ->
                    if (event == SplashContract.Event.OnFirstLaunch) {
                        eventTriggered = true
                    }
                },
            )
        }

        // Then
        assertTrue(eventTriggered)
    }

    @Test
    fun `splashScreen triggers OnAnimationFinished event`() = runComposeUiTest {
        // Given
        var eventTriggered = false

        // When
        setContent {
            SplashScreenContent(
                state = SplashContract.State(),
                event = { event ->
                    if (event == SplashContract.Event.OnAnimationFinished) {
                        eventTriggered = true
                    }
                },
            )
        }
        mainClock.advanceTimeUntil(condition = onNodeWithTag("splash_text")::isDisplayed)

        // Then
        assertTrue(eventTriggered)
    }

    @Test
    fun `splashScreen displays text correctly based on state`() = runComposeUiTest {
        // Given
        val stateWithText = SplashContract.State(shouldDisplayText = true)
        val stateWithoutText = SplashContract.State(shouldDisplayText = false)

        // When
        setContent {
            SplashScreenContent(
                state = stateWithText,
                event = {},
            )
        }

        // Then
        onNodeWithTag("splash_lottie").assertIsDisplayed()
        onNodeWithTag("splash_text").assertIsDisplayed()

        // When
        setContent {
            SplashScreenContent(
                state = stateWithoutText,
                event = {},
            )
        }

        // Then
        onNodeWithTag("splash_lottie").assertIsDisplayed()
        onNodeWithText("splash_text").assertDoesNotExist()
    }
}
