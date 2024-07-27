package dev.kigya.mindplex.feature.onboarding.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.model.OnboardingScreenUiModel
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenContent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.Res
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_next_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_skip_button_text
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class OnboardingScreenTest {

    @Test
    fun `onboardingScreen displays initial state`() = runComposeUiTest {
        // Given & When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_content").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen displays onboarding data`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            ),
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_second_title,
                descriptionTextResource = Res.string.onboarding_second_description
            ),
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_content").assertIsDisplayed()
        onNodeWithTag("onboarding_page_0").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen triggers OnFirstLaunch event`() = runComposeUiTest {
        // Given
        var eventTriggered = false

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(),
                event = { event ->
                    if (event == OnboardingContract.Event.OnFirstLaunch) {
                        eventTriggered = true
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        assertTrue(eventTriggered)
    }

    @Test
    fun `onboardingScreen triggers OnSkipClicked event`() = runComposeUiTest {
        // Given
        var eventTriggered = false
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(skipButtonTextResource = Res.string.onboarding_skip_button_text)
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = { event ->
                    if (event == OnboardingContract.Event.OnSkipClicked) {
                        eventTriggered = true
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_skip_button_0").performClick()
        assertTrue(eventTriggered)
    }

    @Test
    fun `onboardingScreen triggers OnNextClicked event`() = runComposeUiTest {
        // Given
        var eventTriggered = false
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(nextButtonTextResource = Res.string.onboarding_next_button_text)
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = { event ->
                    if (event is OnboardingContract.Event.OnNextClicked) {
                        eventTriggered = true
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_next_button_0").performClick()
        assertTrue(eventTriggered)
    }

    @Test
    fun `onboardingScreen scrolls to next page on ScrollToPage effect`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(nextButtonTextResource = Res.string.onboarding_next_button_text),
            OnboardingScreenUiModel(nextButtonTextResource = Res.string.onboarding_next_button_text),
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = {},
                effect = StableFlow(flowOf(OnboardingContract.Effect.ScrollToPage(2)))
            )
        }

        // Then
        onNodeWithTag("onboarding_page_1").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen displays title when shouldDisplayTitle is true`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayTitle = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_title_0").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen hides title when shouldDisplayTitle is false`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayTitle = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_title_0").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen displays description when shouldDisplayDescription is true`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDescription = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_description_0").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen hides description when shouldDisplayDescription is false`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDescription = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_description_0").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen displays dots indicator when shouldDisplayDotsIndicator is true`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDotsIndicator = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_dots_indicator").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen hides dots indicator when shouldDisplayDotsIndicator is false`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDotsIndicator = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_dots_indicator").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen displays skip and next buttons correctly on first page`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                skipButtonTextResource = Res.string.onboarding_skip_button_text,
                nextButtonTextResource = Res.string.onboarding_next_button_text
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_skip_button_0").assertIsDisplayed()
        onNodeWithTag("onboarding_next_button_0").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen displays only next button correctly on last page`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                nextButtonTextResource = Res.string.onboarding_next_button_text
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_skip_button_0").assertDoesNotExist()
        onNodeWithTag("onboarding_next_button_0").assertIsDisplayed()
    }

    @Test
    fun `onboardingScreen toggles shouldDisplayTitle correctly`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayTitle = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_title_0").assertIsDisplayed()

        // Toggle shouldDisplayTitle to false
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayTitle = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_title_0").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen toggles shouldDisplayDescription correctly`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDescription = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_description_0").assertIsDisplayed()

        // Toggle shouldDisplayDescription to false
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDescription = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_description_0").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen toggles shouldDisplayDotsIndicator correctly`() = runComposeUiTest {
        // Given
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                titleTextResource = Res.string.onboarding_first_title,
                descriptionTextResource = Res.string.onboarding_first_description
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDotsIndicator = true
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_dots_indicator").assertIsDisplayed()

        // Toggle shouldDisplayDotsIndicator to false
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(
                    onboardingData = onboardingData,
                    shouldDisplayDotsIndicator = false
                ),
                event = {},
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_dots_indicator").assertDoesNotExist()
    }

    @Test
    fun `onboardingScreen updates state and navigates correctly when next button is clicked`() = runComposeUiTest {
        // Given
        var currentPage = 0
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                nextButtonTextResource = Res.string.onboarding_next_button_text
            ),
            OnboardingScreenUiModel(
                nextButtonTextResource = Res.string.onboarding_next_button_text
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = { event ->
                    if (event is OnboardingContract.Event.OnNextClicked) {
                        currentPage = event.currentPage + 1
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Click next button on first page
        onNodeWithTag("onboarding_next_button_0").performClick()

        // Then
        assertTrue(currentPage == 1)

        // Click next button on second page
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = { event ->
                    if (event is OnboardingContract.Event.OnNextClicked) {
                        currentPage = event.currentPage + 1
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_next_button_0").performClick()
        assertTrue(currentPage == 1)
    }

    @Test
    fun `onboardingScreen navigates correctly when skip button is clicked`() = runComposeUiTest {
        // Given
        var eventTriggered = false
        val onboardingData = persistentListOf(
            OnboardingScreenUiModel(
                skipButtonTextResource = Res.string.onboarding_skip_button_text
            )
        )

        // When
        setContent {
            OnboardingScreenContent(
                state = OnboardingContract.State(onboardingData = onboardingData),
                event = { event ->
                    if (event == OnboardingContract.Event.OnSkipClicked) {
                        eventTriggered = true
                    }
                },
                effect = StableFlow(emptyFlow())
            )
        }

        // Then
        onNodeWithTag("onboarding_skip_button_0").performClick()
        assertTrue(eventTriggered)
    }
}
