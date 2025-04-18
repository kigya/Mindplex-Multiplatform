package dev.kigya.mindplex.core.data.profile.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.kigya.mindplex.core.domain.profile.contract.ThemePreferencesRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ThemePreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher,
) : ThemePreferencesRepositoryContract {

    override val isInDarkTheme: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(THEME_KEY)] == true
        }

    override suspend fun saveTheme(isDark: Boolean) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(THEME_KEY)] = isDark
            }
        }
    }

    private companion object {
        const val THEME_KEY = "theme_key"
    }
}
