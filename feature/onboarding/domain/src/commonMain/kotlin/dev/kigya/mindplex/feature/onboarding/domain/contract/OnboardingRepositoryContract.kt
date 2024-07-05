package dev.kigya.mindplex.feature.onboarding.domain.contract

import kotlinx.coroutines.flow.Flow

interface OnboardingRepositoryContract {
    val isOnboardingCompleted: Flow<Boolean>

    suspend fun setOnboardingCompleted()
}
