package dev.kigya.mindplex.feature.onboarding.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract

class SetOnboardingCompletedUseCase(
    private val onboardingRepositoryContract: OnboardingRepositoryContract,
) : BaseSuspendUseCase<Unit, None>() {

    override suspend operator fun invoke(params: None) =
        onboardingRepositoryContract.setOnboardingCompleted()
}
