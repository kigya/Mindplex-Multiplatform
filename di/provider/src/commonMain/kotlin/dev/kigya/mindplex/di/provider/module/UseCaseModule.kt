package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetIsOnboardingCompletedUseCase)
    singleOf(::SetOnboardingCompletedUseCase)
}
