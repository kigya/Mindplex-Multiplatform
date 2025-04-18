package dev.kigya.mindplex.feature.login.data.repository.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SignInPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : SignInPreferencesRepositoryContract {

    override val userToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(GOOGLE_ID_TOKEN)]
        }

    override val isSignedIn: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(IS_SIGNED_IN)]?.toBoolean() ?: false
        }

    override suspend fun signIn(googleIdToken: String) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(GOOGLE_ID_TOKEN)] = googleIdToken
                preferences[stringPreferencesKey(IS_SIGNED_IN)] = "true"
            }
        }
    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(GOOGLE_ID_TOKEN)] = String.empty
                preferences[stringPreferencesKey(IS_SIGNED_IN)] = "false"
            }
        }
    }

    private companion object {
        const val GOOGLE_ID_TOKEN = "google_id_token"
        const val IS_SIGNED_IN = "is_signed_in"
    }
}
