package dev.kigya.mindplex.di.internal.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.core.data.connectivity.ConnectivityRepository
import dev.kigya.mindplex.core.data.profile.repository.UserProfileDatabaseRepository
import dev.kigya.mindplex.core.data.profile.repository.UserProfileNetworkRepository
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.data.repository.QuestionsDatabaseRepository
import dev.kigya.mindplex.feature.game.data.repository.QuestionsNetworkRepository
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.game.domain.contract.QuestionsNetworkRepositoryContract
import dev.kigya.mindplex.feature.home.data.repository.FactsDatabaseRepository
import dev.kigya.mindplex.feature.home.data.repository.FactsNetworkRepository
import dev.kigya.mindplex.feature.home.domain.contract.FactsDatabaseRepositoryContract
import dev.kigya.mindplex.feature.home.domain.contract.FactsNetworkRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.data.repository.UserRankDatabaseRepository
import dev.kigya.mindplex.feature.leaderboard.data.repository.UserRankNetworkRepository
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankDatabaseRepositoryContract
import dev.kigya.mindplex.feature.leaderboard.domain.contract.UserRankNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.data.repository.interceptor.ProfileImageInterceptor
import dev.kigya.mindplex.feature.login.data.repository.repository.GeoLocationRepository
import dev.kigya.mindplex.feature.login.data.repository.repository.SignInNetworkRepository
import dev.kigya.mindplex.feature.login.data.repository.repository.SignInPreferencesRepository
import dev.kigya.mindplex.feature.login.domain.contract.GeoLocationContract
import dev.kigya.mindplex.feature.login.domain.contract.ProfileImageInterceptorContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
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
            dataStore = get<DataStore<Preferences>>(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind OnboardingRepositoryContract::class

    single {
        SignInPreferencesRepository(
            dataStore = get<DataStore<Preferences>>(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind SignInPreferencesRepositoryContract::class

    single {
        SignInNetworkRepository(
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind SignInNetworkRepositoryContract::class

    single {
        ConnectivityRepository(connectivityManager = get())
    } bind ConnectivityRepositoryContract::class

    single {
        UserProfileNetworkRepository(
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind UserProfileNetworkRepositoryContract::class

    single {
        UserProfileDatabaseRepository(
            userProfileDao = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind UserProfileDatabaseRepositoryContract::class

    single {
        FactsNetworkRepository(
            httpClient = get(),
            secretsProviderContract = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind FactsNetworkRepositoryContract::class

    single {
        FactsDatabaseRepository(
            factsDao = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind FactsDatabaseRepositoryContract::class

    single {
        QuestionsDatabaseRepository(
            questionDao = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind QuestionsDatabaseRepositoryContract::class

    single {
        QuestionsNetworkRepository(
            httpClient = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind QuestionsNetworkRepositoryContract::class

    single {
        ProfileImageInterceptor()
    } bind ProfileImageInterceptorContract::class

    single {
        UserRankDatabaseRepository(
            userRankDao = get(),
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind UserRankDatabaseRepositoryContract::class

    single {
        UserRankNetworkRepository(
            dispatcher = get(qualifier = named(Dispatchers.IO::class.simpleName.orEmpty())),
        )
    } bind UserRankNetworkRepositoryContract::class

    single {
        GeoLocationRepository(
            httpClient = get(),
            secretsProviderContract = get(),
        )
    } bind GeoLocationContract::class
}
