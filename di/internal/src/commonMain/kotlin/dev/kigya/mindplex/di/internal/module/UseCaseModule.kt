package dev.kigya.mindplex.di.internal.module

import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.GetQuestionUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.GetScoreUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.UpdateScoreUseCase
import dev.kigya.mindplex.feature.game.domain.usecase.ValidateQuestionUseCase
import dev.kigya.mindplex.feature.home.domain.usecase.GetFactsUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.GetIsUserSignedInUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.GetUserCountryCodeUseCase
import dev.kigya.mindplex.feature.login.domain.usecase.SignInUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.GetIsOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.onboarding.domain.usecase.SetOnboardingCompletedUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.GetProfileUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.GetThemeUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.GetUsersByRankUseCase
import dev.kigya.mindplex.feature.profile.domain.usecase.SaveThemeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetIsOnboardingCompletedUseCase)
    factoryOf(::SetOnboardingCompletedUseCase)
    factoryOf(::SignInUseCase)
    factoryOf(::GetIsUserSignedInUseCase)
    factoryOf(::GetUserProfileUseCase)
    factoryOf(::GetFactsUseCase)
    factoryOf(::GetQuestionUseCase)
    factoryOf(::ValidateQuestionUseCase)
    factoryOf(::GetScoreUseCase)
    factoryOf(::UpdateScoreUseCase)
    factoryOf(::GetUsersByRankUseCase)
    factoryOf(::GetUserCountryCodeUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::GetThemeUseCase)
    factoryOf(::SaveThemeUseCase)
}
