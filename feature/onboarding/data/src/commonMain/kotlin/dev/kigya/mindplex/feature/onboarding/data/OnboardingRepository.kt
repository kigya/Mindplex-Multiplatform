package dev.kigya.mindplex.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.core.data.preferences.delegate.flowPreference
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher

class OnboardingRepository(
    dataStore: DataStore<Preferences>,
    dispatcher: CoroutineDispatcher,
) : OnboardingRepositoryContract {

    override var isOnboardingCompleted by dataStore.flowPreference<Boolean>(
        key = IS_ONBOARDING_COMPLETED_KEY,
        defaultValue = false,
        coroutineContext = dispatcher,
    )

    private companion object {
        const val IS_ONBOARDING_COMPLETED_KEY = "is_onboarding_completed"
    }
}
