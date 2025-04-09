package dev.kigya.mindplex.feature.profile.domain.contract

interface PreferencesRepositoryContract {
    suspend fun getTheme(): Boolean?
    suspend fun saveTheme(isDark: Boolean)
}
