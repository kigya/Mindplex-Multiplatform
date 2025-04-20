package dev.kigya.mindplex.feature.onboarding.domain

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.outcome.outcomeCatching
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class SetOnboardingCompletedUseCaseTest {

    private val onboardingRepositoryContract = mockk<OnboardingRepositoryContract>(relaxed = true)
    private var setOnboardingCompletedUseCase by Delegates.notNull<SetOnboardingCompletedUseCase>()

    @Before
    fun setUp() {
        setOnboardingCompletedUseCase = SetOnboardingCompletedUseCase(onboardingRepositoryContract)
    }

    @Test
    fun `invoke calls repository to set onboarding completed`() = runTest {
        // When
        setOnboardingCompletedUseCase(None)

        // Then
        coVerify { onboardingRepositoryContract.setOnboardingCompleted() }
    }

    @Test
    fun `invoke is idempotent`() = runTest {
        // When
        setOnboardingCompletedUseCase(None)
        setOnboardingCompletedUseCase(None)

        // Then
        coVerify(exactly = 2) { onboardingRepositoryContract.setOnboardingCompleted() }
    }

    @Test
    fun `invoke handles repository errors`() = runTest {
        // Given
        coEvery { onboardingRepositoryContract.setOnboardingCompleted() } throws RuntimeException("Test exception")

        // When
        outcomeCatching { setOnboardingCompletedUseCase(None) }

        // Then
        coVerify { onboardingRepositoryContract.setOnboardingCompleted() }
    }
}
