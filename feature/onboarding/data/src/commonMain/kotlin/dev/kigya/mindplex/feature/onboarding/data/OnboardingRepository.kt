package dev.kigya.mindplex.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OnboardingRepository(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : OnboardingRepositoryContract {

    override val isOnboardingCompleted: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(IS_ONBOARDING_COMPLETED_KEY)] == true
        }

    override suspend fun setOnboardingCompleted() {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(IS_ONBOARDING_COMPLETED_KEY)] = true
            }
        }
    }

    private companion object {
        const val IS_ONBOARDING_COMPLETED_KEY = "is_onboarding_completed"
    }
}
