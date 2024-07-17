package dev.kigya.mindplex.feature.onboarding.domain

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class GetIsOnboardingCompletedUseCaseTest {

    private val onboardingRepositoryContract = mockk<OnboardingRepositoryContract>()
    private var getIsOnboardingCompletedUseCase by Delegates.notNull<GetIsOnboardingCompletedUseCase>()

    @Before
    fun setUp() {
        getIsOnboardingCompletedUseCase = GetIsOnboardingCompletedUseCase(onboardingRepositoryContract)
    }

    @Test
    fun `invoke returns flow from repository`() = runTest {
        // Given
        val expectedFlow = flowOf(true)
        every { onboardingRepositoryContract.isOnboardingCompleted } returns expectedFlow

        // When
        val result = getIsOnboardingCompletedUseCase(None)

        // Then
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `invoke returns false when onboarding is not completed`() = runTest {
        // Given
        every { onboardingRepositoryContract.isOnboardingCompleted } returns flowOf(false)

        // When
        val result = getIsOnboardingCompletedUseCase(None).first()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `invoke returns true when onboarding is completed`() = runTest {
        // Given
        every { onboardingRepositoryContract.isOnboardingCompleted } returns flowOf(true)

        // When
        val result = getIsOnboardingCompletedUseCase(None).first()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `invoke updates flow when data store value changes`() = runTest {
        // Given
        val flow = MutableStateFlow(false)
        every { onboardingRepositoryContract.isOnboardingCompleted } returns flow

        // When
        val resultFlow = getIsOnboardingCompletedUseCase(None)

        // Simulate data store value change
        flow.value = true

        // Then
        assertEquals(true, resultFlow.first())
    }
}
