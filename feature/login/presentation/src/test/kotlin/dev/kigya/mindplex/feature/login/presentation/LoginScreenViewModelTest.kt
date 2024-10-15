package dev.kigya.mindplex.feature.login.presentation

import arrow.core.raise.either
import arrow.core.right
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.SignInUseCase
import dev.kigya.mindplex.feature.login.presentation.contract.LoginContract
import dev.kigya.mindplex.feature.login.presentation.mapper.toDomain
import dev.kigya.mindplex.feature.login.presentation.ui.LoginScreenViewModel
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
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
class LoginScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val navigatorContract = mockk<AppNavigatorContract>(relaxed = true)
    private val signInUseCase = mockk<SignInUseCase>()
    private val getIsUserSignedInUseCase = mockk<GetIsUserSignedInUseCase>()

    private var viewModel by Delegates.notNull<LoginScreenViewModel>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginScreenViewModel(
            navigatorContract = navigatorContract,
            signInUseCase = signInUseCase,
            getIsUserSignedInUseCase = getIsUserSignedInUseCase,
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
        assertThat(viewModel.state.value.stubErrorType).isNull()
    }

    @Test
    fun `handleEvent triggers navigation on OnFirstLaunch when user is signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(true) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
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
    fun `handleEvent does not trigger navigation on OnFirstLaunch when user is not signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(false) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 0) {
                navigatorContract.navigateTo(any(), any(), any())
            }
        }

    @Test
    fun `handleEvent updates state correctly on OnGoogleSignInResultReceived with failure due to network`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "idToken")
            coEvery { signInUseCase(any()) } returns either { raise(MindplexDomainError.NETWORK) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isEqualTo(StubErrorType.NETWORK)
        }

    @Test
    fun `handleEvent updates state correctly on OnGoogleSignInResultReceived with failure due to other reasons`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "idToken")
            coEvery { signInUseCase(any()) } returns either { raise(MindplexDomainError.OTHER) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isEqualTo(StubErrorType.UNSPECIFIED)
        }

    @Test
    fun `handleEvent does not update state on OnGoogleSignInResultReceived with success`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "idToken")
            coEvery { signInUseCase(googleUser.toDomain()) } returns either { Unit.right() }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isNull()
        }

    @Test
    fun `handleEvent updates state correctly on OnErrorStubClicked`() = testScope.runTest {
        // Given
        val googleUser = GoogleUser(idToken = "idToken")
        coEvery { signInUseCase(googleUser.toDomain()) } returns either { Unit.right() }
        viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
        advanceUntilIdle()

        // When
        viewModel.handleEvent(LoginContract.Event.OnErrorStubClicked)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.stubErrorType).isNull()
    }

    @Test
    fun `handleEvent updates state correctly on OnFirstLaunch when user is signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(true) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isNull()
        }

    @Test
    fun `handleEvent updates state correctly on OnFirstLaunch when user is not signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(false) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isNull()
        }

    @Test
    fun `handleEvent does not update state on OnGoogleSignInResultReceived with null user`() =
        testScope.runTest {
            // Given
            val googleUser: GoogleUser? = null
            coEvery { signInUseCase(googleUser.toDomain()) } returns either { Unit.right() }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isNull()
        }

    @Test
    fun `handleEvent updates state correctly on OnGoogleSignInResultReceived with invalid token`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "invalidToken")
            coEvery { signInUseCase(any()) } returns either { raise(MindplexDomainError.OTHER) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isEqualTo(StubErrorType.UNSPECIFIED)
        }

    @Test
    fun `handleEvent navigates to Home on OnFirstLaunch when user is already signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(true) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
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
    fun `handleEvent updates state correctly on OnGoogleSignInResultReceived with network failure`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "idToken")
            coEvery { signInUseCase(googleUser.toDomain()) } returns either { raise(MindplexDomainError.NETWORK) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isEqualTo(StubErrorType.NETWORK)
        }

    @Test
    fun `handleEvent updates state correctly on OnGoogleSignInResultReceived with unspecified failure`() =
        testScope.runTest {
            // Given
            val googleUser = GoogleUser(idToken = "idToken")
            coEvery { signInUseCase(googleUser.toDomain()) } returns either { raise(MindplexDomainError.OTHER) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnGoogleSignInResultReceived(googleUser))
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isEqualTo(StubErrorType.UNSPECIFIED)
        }

    @Test
    fun `handleEvent does not change state on OnErrorStubClicked when there is no error`() =
        testScope.runTest {
            // Given
            assertThat(viewModel.state.value.stubErrorType).isNull()

            // When
            viewModel.handleEvent(LoginContract.Event.OnErrorStubClicked)
            advanceUntilIdle()

            // Then
            assertThat(viewModel.state.value.stubErrorType).isNull()
        }

    @Test
    fun `handleEvent does not navigate to Home on OnFirstLaunch when user is not signed in`() =
        testScope.runTest {
            // Given
            coEvery { getIsUserSignedInUseCase.invoke(None) } returns flow { emit(false) }

            // When
            viewModel.handleEvent(LoginContract.Event.OnFirstLaunch)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 0) {
                navigatorContract.navigateTo(
                    route = ScreenRoute.Home,
                    popUpToRoute = ScreenRoute.Splash,
                    inclusive = true,
                )
            }
        }
}
