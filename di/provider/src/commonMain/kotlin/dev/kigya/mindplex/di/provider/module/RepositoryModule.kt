package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.feature.onboarding.data.OnboardingRepository
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        OnboardingRepository(
            dataStore = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind OnboardingRepositoryContract::class
}
