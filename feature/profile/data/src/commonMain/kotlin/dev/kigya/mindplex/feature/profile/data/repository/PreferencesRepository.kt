package dev.kigya.mindplex.feature.profile.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.kigya.mindplex.feature.profile.domain.contract.PreferencesRepositoryContract
import kotlinx.coroutines.flow.first

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) : PreferencesRepositoryContract {

    private val themeKey = booleanPreferencesKey("theme_key")

    override suspend fun getTheme(): Boolean? {
        val preferences = dataStore.data.first()
        return preferences[themeKey]
    }

    override suspend fun saveTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDark
        }
    }
}
