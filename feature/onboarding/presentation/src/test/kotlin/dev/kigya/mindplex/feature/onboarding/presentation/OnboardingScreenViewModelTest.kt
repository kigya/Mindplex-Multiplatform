package dev.kigya.mindplex.feature.onboarding.presentation

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenViewModel
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.properties.Delegates
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val navigatorContract = mockk<AppNavigatorContract>(relaxed = true)
    private val setOnboardingCompletedUseCase = mockk<SetOnboardingCompletedUseCase>()

    private var viewModel by Delegates.notNull<OnboardingScreenViewModel>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingScreenViewModel(
            navigatorContract = navigatorContract,
            setOnboardingCompletedUseCase = setOnboardingCompletedUseCase,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }

    @Test
    fun `initial state is correct`() {
        // Given
        // ViewModel creation

        // Then
        assertThat(viewModel.state.value.onboardingData).isEqualTo(persistentListOf())
        assertThat(viewModel.state.value.shouldDisplayTitle).isEqualTo(false)
        assertThat(viewModel.state.value.shouldDisplayDescription).isEqualTo(false)
        assertThat(viewModel.state.value.shouldDisplayDotsIndicator).isEqualTo(false)
    }

    @Test
    fun `handleEvent updates state correctly on OnFirstLaunch`() = testScope.runTest {
        // Given

        // When
        viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.onboardingData).isNotEmpty()
        assertThat(viewModel.state.value.shouldDisplayTitle).isEqualTo(true)
        assertThat(viewModel.state.value.shouldDisplayDescription).isEqualTo(true)
        assertThat(viewModel.state.value.shouldDisplayDotsIndicator).isEqualTo(true)
    }

    @Test
    fun `handleEvent triggers ScrollToPage effect on OnNextClicked`() = testScope.runTest {
        // Given
        val effects = mutableListOf<OnboardingContract.Effect>()
        val job = launch {
            viewModel.effect.toList(effects)
        }

        // When
        viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
        viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(0))
        advanceUntilIdle()

        // Then
        assertThat(effects).containsExactly(OnboardingContract.Effect.ScrollToPage(1))
        job.cancel()
    }

    @Test
    fun `handleEvent triggers ScrollToPage effects on multiple OnNextClicked`() =
        testScope.runTest {
            // Given
            val effects = mutableListOf<OnboardingContract.Effect>()
            val job = launch {
                viewModel.effect.toList(effects)
            }

            // When
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(0))
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(1))
            advanceUntilIdle()

            // Then
            assertThat(effects).containsExactly(
                OnboardingContract.Effect.ScrollToPage(1),
                OnboardingContract.Effect.ScrollToPage(2),
            )

            job.cancel()
        }

    @Test
    fun `handleEvent sets onboarding complete and navigates to login on OnNextClicked at last page`() =
        testScope.runTest {
            // Given
            coEvery { setOnboardingCompletedUseCase(None) } returns Unit

            // When
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(2))
            advanceUntilIdle()

            // Then
            coVerify { setOnboardingCompletedUseCase(None) }
            coVerify {
                navigatorContract.navigateTo(
                    route = ScreenRoute.LOGIN,
                    popUpToRoute = ScreenRoute.SPLASH,
                    inclusive = true,
                )
            }
        }

    @Test
    fun `handleEvent sets onboarding complete and navigates to login on OnSkipClicked`() =
        testScope.runTest {
            // Given
            coEvery { setOnboardingCompletedUseCase(None) } returns Unit

            // When
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnSkipClicked)
            advanceUntilIdle()

            // Then
            coVerify { setOnboardingCompletedUseCase(None) }
            coVerify {
                navigatorContract.navigateTo(
                    route = ScreenRoute.LOGIN,
                    popUpToRoute = ScreenRoute.SPLASH,
                    inclusive = true,
                )
            }
        }

    @Test
    fun `handleEvent triggers ScrollToPage on OnNextClicked when not on last page`() =
        testScope.runTest {
            // Given
            val effects = mutableListOf<OnboardingContract.Effect>()
            val job = launch {
                viewModel.effect.toList(effects)
            }

            // When
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(0))
            advanceUntilIdle()

            // Then
            assertThat(effects).containsExactly(OnboardingContract.Effect.ScrollToPage(1))

            job.cancel()
        }

    @Test
    fun `handleEvent updates state correctly on multiple OnFirstLaunch events`() =
        testScope.runTest {
            // Given

            // When
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()
            viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.onboardingData).isNotEmpty()
            assertThat(viewModel.state.value.shouldDisplayTitle).isEqualTo(true)
            assertThat(viewModel.state.value.shouldDisplayDescription).isEqualTo(true)
            assertThat(viewModel.state.value.shouldDisplayDotsIndicator).isEqualTo(true)
        }

    @Test
    fun `handleEvent does not update state on OnNextClicked at last page`() = testScope.runTest {
        // Given
        viewModel.handleEvent(OnboardingContract.Event.OnFirstLaunch)
        advanceUntilIdle()
        val initialState = viewModel.state.value
        coEvery { setOnboardingCompletedUseCase(None) } returns Unit

        // When
        viewModel.handleEvent(OnboardingContract.Event.OnNextClicked(initialState.onboardingData.lastIndex))
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value).isEqualTo(initialState)
    }
}
