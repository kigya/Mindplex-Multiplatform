package dev.kigya.mindplex.feature.login.domain

import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel
import dev.kigya.mindplex.feature.login.domain.usecase.SignInUseCase
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class SignInUseCaseTest {

    private var signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract by Delegates.notNull()
    private var signInNetworkRepositoryContract: SignInNetworkRepositoryContract by Delegates.notNull()
    private var jwtHandlerContract: JwtHandlerContract by Delegates.notNull()
    private var connectivityRepositoryContract: ConnectivityRepositoryContract by Delegates.notNull()
    private var signInUseCase: SignInUseCase by Delegates.notNull()

    @Before
    fun setup() {
        signInPreferencesRepositoryContract = mockk(relaxed = true)
        signInNetworkRepositoryContract = mockk(relaxed = true)
        jwtHandlerContract = mockk()
        connectivityRepositoryContract = mockk()
        signInUseCase = SignInUseCase(
            signInPreferencesRepositoryContract,
            signInNetworkRepositoryContract,
            jwtHandlerContract,
            connectivityRepositoryContract,
        )
    }

    @Test
    fun `invoke returns Success when user is signed in successfully`() = runTest {
        // Given
        val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
        val decodedUserId = "userId"
        coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.success(decodedUserId)
        coEvery { signInPreferencesRepositoryContract.signIn(any()) } returns Unit
        coEvery { signInNetworkRepositoryContract.signIn(any()) } returns Unit

        // When
        val result = signInUseCase(googleUser)

        // Then
        assertEquals(GoogleSignInDomainResult.Success, result)
        coVerify { jwtHandlerContract.decodeSubject(googleUser.tokenId) }
        coVerify { signInPreferencesRepositoryContract.signIn(decodedUserId) }
        coVerify { signInNetworkRepositoryContract.signIn(googleUser.copy(tokenId = decodedUserId)) }
    }

    @Test
    fun `invoke returns Failure NETWORK when there is no connectivity`() = runTest {
        // Given
        coEvery { connectivityRepositoryContract.isConnected() } returns false

        // When
        val result = signInUseCase(null)

        // Then
        assertEquals(
            GoogleSignInDomainResult.Failure(GoogleSignInDomainResult.GoogleSignInDomainFailureReason.NETWORK),
            result,
        )
    }

    @Test
    fun `invoke returns Failure OTHER when there is connectivity but params are null`() = runTest {
        // Given
        coEvery { connectivityRepositoryContract.isConnected() } returns true

        // When
        val result = signInUseCase(null)

        // Then
        assertEquals(
            GoogleSignInDomainResult.Failure(GoogleSignInDomainResult.GoogleSignInDomainFailureReason.OTHER),
            result,
        )
    }

    @Test
    fun `invoke returns Failure OTHER when jwtHandler fails`() = runTest {
        // Given
        val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
        coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.failure(Exception("JWT decoding failed"))
        coEvery { connectivityRepositoryContract.isConnected() } returns true

        // When
        val result = signInUseCase(googleUser)

        // Then
        assertEquals(
            GoogleSignInDomainResult.Failure(GoogleSignInDomainResult.GoogleSignInDomainFailureReason.OTHER),
            result,
        )
    }

    @Test
    fun `invoke calls signInNetworkRepositoryContract with correct user`() = runTest {
        // Given
        val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
        val decodedUserId = "userId"
        coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.success(decodedUserId)
        coEvery { signInPreferencesRepositoryContract.signIn(any()) } returns Unit
        coEvery { signInNetworkRepositoryContract.signIn(any()) } returns Unit

        // When
        signInUseCase(googleUser)

        // Then
        coVerify { signInNetworkRepositoryContract.signIn(googleUser.copy(tokenId = decodedUserId)) }
    }

    @Test
    fun `invoke calls signInPreferencesRepositoryContract with correct token`() = runTest {
        // Given
        val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
        val decodedUserId = "userId"
        coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.success(decodedUserId)
        coEvery { signInPreferencesRepositoryContract.signIn(any()) } returns Unit
        coEvery { signInNetworkRepositoryContract.signIn(any()) } returns Unit

        // When
        signInUseCase(googleUser)

        // Then
        coVerify { signInPreferencesRepositoryContract.signIn(decodedUserId) }
    }

    @Test
    fun `invoke does not call connectivityRepositoryContract isConnected if params are not null`() =
        runTest {
            // Given
            val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
            val decodedUserId = "userId"
            coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.success(decodedUserId)
            coEvery { signInPreferencesRepositoryContract.signIn(any()) } returns Unit
            coEvery { signInNetworkRepositoryContract.signIn(any()) } returns Unit

            // When
            signInUseCase(googleUser)

            // Then
            coVerify(exactly = 0) { connectivityRepositoryContract.isConnected() }
        }

    @Test
    fun `invoke calls connectivityRepositoryContract isConnected if params are null`() = runTest {
        // Given
        coEvery { connectivityRepositoryContract.isConnected() } returns true

        // When
        signInUseCase(null)

        // Then
        coVerify { connectivityRepositoryContract.isConnected() }
    }

    @Test
    fun `invoke calls signInPreferencesRepositoryContract and signInNetworkRepositoryContract in correct order`() =
        runTest {
            // Given
            val googleUser = GoogleUserSignInDomainModel(tokenId = "tokenId")
            val decodedUserId = "userId"
            coEvery { jwtHandlerContract.decodeSubject(any()) } returns Result.success(decodedUserId)
            coEvery { signInPreferencesRepositoryContract.signIn(any()) } returns Unit
            coEvery { signInNetworkRepositoryContract.signIn(any()) } returns Unit

            // When
            signInUseCase(googleUser)

            // Then
            coVerify(ordering = Ordering.ORDERED) {
                signInPreferencesRepositoryContract.signIn(decodedUserId)
                signInNetworkRepositoryContract.signIn(googleUser.copy(tokenId = decodedUserId))
            }
        }
}
