package dev.kigya.mindplex.feature.onboarding.domain.contract

import kotlinx.coroutines.flow.Flow

interface OnboardingRepositoryContract {
    var isOnboardingCompleted: Flow<Boolean>
}
