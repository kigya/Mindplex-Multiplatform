package dev.kigya.mindplex.feature.login.domain

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class GetIsUserSignedInUseCaseTest {

    private var signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract by Delegates.notNull()
    private var getIsUserSignedInUseCase: GetIsUserSignedInUseCase by Delegates.notNull()

    @Before
    fun setup() {
        signInPreferencesRepositoryContract = mockk()
        getIsUserSignedInUseCase = GetIsUserSignedInUseCase(signInPreferencesRepositoryContract)
    }

    @Test
    fun `invoke returns isSignedIn flow`() = runTest {
        // Given
        val expectedFlow: Flow<Boolean> = flowOf(true)
        coEvery { signInPreferencesRepositoryContract.isSignedIn } returns expectedFlow

        // When
        val result = getIsUserSignedInUseCase(None)

        // Then
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `invoke returns isSignedIn flow when user is not signed in`() = runTest {
        // Given
        val expectedFlow: Flow<Boolean> = flowOf(false)
        coEvery { signInPreferencesRepositoryContract.isSignedIn } returns expectedFlow

        // When
        val result = getIsUserSignedInUseCase(None)

        // Then
        assertEquals(expectedFlow, result)
        assertTrue(result.toList().contains(false))
    }

    @Test
    fun `invoke returns isSignedIn flow with multiple emissions`() = runTest {
        // Given
        val expectedFlow: Flow<Boolean> = flow {
            emit(true)
            emit(false)
        }
        coEvery { signInPreferencesRepositoryContract.isSignedIn } returns expectedFlow

        // When
        val result = getIsUserSignedInUseCase(None).toList()

        // Then
        assertEquals(listOf(true, false), result)
    }
}
