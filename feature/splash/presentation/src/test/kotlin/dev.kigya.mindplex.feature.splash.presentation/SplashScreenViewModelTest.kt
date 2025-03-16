package dev.kigya.mindplex.feature.splash.presentation

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.SplashScreenViewModel
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
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

class SplashScreenViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val navigatorContract = mockk<MindplexNavigatorContract>(relaxed = true)
    private val getIsOnboardingCompletedUseCase = mockk<GetIsOnboardingCompletedUseCase>()
    private val getIsUserSignedInUseCase = mockk<GetIsUserSignedInUseCase>()

    private var viewModel by Delegates.notNull<SplashScreenViewModel>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashScreenViewModel(
            navigatorContract = navigatorContract,
            getIsOnboardingCompletedUseCase = getIsOnboardingCompletedUseCase,
            getIsUserSignedInUseCase = getIsUserSignedInUseCase,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }

    @Test
    fun `test _isOnboardingCompleted is updated before _isUserSignedIn`() = testScope.runTest {
        // Given
        coEvery { getIsOnboardingCompletedUseCase(None) }.returns(flowOf(true))
        coEvery { getIsUserSignedInUseCase(None) }.returns(flowOf(true))

        // When
        viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

        // Then
        verifySequence {
            getIsOnboardingCompletedUseCase(None)
            getIsUserSignedInUseCase(None)
        }
    }

    @Test
    fun `shouldDisplayText is false before OnAnimationFinished`() = testScope.runTest {
        // Given
        coEvery { getIsOnboardingCompletedUseCase(None) }.returns(flowOf(true))
        coEvery { getIsUserSignedInUseCase(None) }.returns(flowOf(true))

        // When
        viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

        // Then
        assertThat(viewModel.state.value.shouldDisplayText).isEqualTo(false)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `shouldDisplayText is true after OnAnimationFinished`() = testScope.runTest {
        // Given
        coEvery { getIsOnboardingCompletedUseCase(None) }.returns(flowOf(true))
        coEvery { getIsUserSignedInUseCase(None) }.returns(flowOf(true))

        // Simulate first launch
        viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

        // When
        viewModel.handleEvent(SplashContract.Event.OnAnimationFinished)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.shouldDisplayText).isEqualTo(true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `route is ONBOARDING when _isOnboardingCompleted is false`() = testScope.runTest {
        // Given
        coEvery { getIsOnboardingCompletedUseCase(None) } returns flowOf(false)
        coEvery { getIsUserSignedInUseCase(None) } returns flowOf(true)

        // Simulate first launch
        viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

        // When
        viewModel.handleEvent(SplashContract.Event.OnAnimationFinished)
        advanceUntilIdle()

        // Then
        coVerify {
            navigatorContract.navigateTo(
                route = ScreenRoute.Onboarding,
                popUpToRoute = ScreenRoute.Splash,
                inclusive = true,
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `route is LOGIN when _isOnboardingCompleted is true and _isUserSignedIn is false`() =
        testScope.runTest {
            // Given
            coEvery { getIsOnboardingCompletedUseCase(None) } returns flowOf(true)
            coEvery { getIsUserSignedInUseCase(None) } returns flowOf(false)

            // Simulate first launch
            viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

            // When
            viewModel.handleEvent(SplashContract.Event.OnAnimationFinished)
            advanceUntilIdle()

            // Then
            coVerify {
                navigatorContract.navigateTo(
                    route = ScreenRoute.Login,
                    popUpToRoute = ScreenRoute.Splash,
                    inclusive = true,
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `route is HOME when both _isOnboardingCompleted and _isUserSignedIn are true`() =
        testScope.runTest {
            // Given
            coEvery { getIsOnboardingCompletedUseCase(None) } returns flowOf(true)
            coEvery { getIsUserSignedInUseCase(None) } returns flowOf(true)

            // Simulate first launch
            viewModel.handleEvent(SplashContract.Event.OnFirstLaunch)

            // When
            viewModel.handleEvent(SplashContract.Event.OnAnimationFinished)
            advanceUntilIdle()

            // Then
            coVerify {
                navigatorContract.navigateTo(
                    route = ScreenRoute.Home,
                    popUpToRoute = ScreenRoute.Splash,
                    inclusive = true,
                )
            }
        }

    @Test
    fun `initial state is correct`() {
        // Given
        // ViewModel creation

        // Then
        assertThat(viewModel.state.value.shouldDisplayText).isEqualTo(false)
    }
}
