package dev.kigya.mindplex.feature.login.data.repository.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SignInPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : SignInPreferencesRepositoryContract {

    override val userId: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(MINDPLEX_JWT)]
        }

    override val isSignedIn: Flow<Boolean>
        get() = userId.map { it != null }

    override suspend fun signIn(googleIdToken: String) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(MINDPLEX_JWT)] = googleIdToken
            }
        }
    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(MINDPLEX_JWT))
            }
        }
    }

    private companion object {
        const val MINDPLEX_JWT = "mindplex_jwt"
    }
}
