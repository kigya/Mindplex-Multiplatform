package dev.kigya.mindplex.di.provider.module

import dev.kigya.mindplex.core.data.connectivity.ConnectivityRepository
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.feature.login.data.repository.SignInPreferencesRepository
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
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

    single {
        SignInPreferencesRepository(
            dataStore = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind SignInPreferencesRepositoryContract::class

    single {
        ConnectivityRepository(connectivityManager = get())
    } bind ConnectivityRepositoryContract::class
}
