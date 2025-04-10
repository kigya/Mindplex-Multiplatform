package dev.kigya.mindplex.feature.onboarding.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.flow.Flow

class GetIsOnboardingCompletedUseCase(
    private val onboardingRepositoryContract: OnboardingRepositoryContract,
) : BaseUseCase<Flow<Boolean>, None>() {

    override suspend operator fun invoke(params: None): Flow<Boolean> =
        onboardingRepositoryContract.isOnboardingCompleted
}
