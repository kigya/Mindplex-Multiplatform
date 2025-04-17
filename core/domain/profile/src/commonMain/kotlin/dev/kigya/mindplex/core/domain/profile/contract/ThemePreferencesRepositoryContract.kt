package dev.kigya.mindplex.core.domain.profile.contract

import kotlinx.coroutines.flow.Flow

interface ThemePreferencesRepositoryContract {
    val isInDarkTheme: Flow<Boolean>

    suspend fun saveTheme(isDark: Boolean)
}
