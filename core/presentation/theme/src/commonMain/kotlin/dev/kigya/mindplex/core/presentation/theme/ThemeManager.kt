package dev.kigya.mindplex.core.presentation.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ThemeManager {
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> get() = _isDarkTheme

    fun getTheme(isSystemTheme: Boolean): Boolean = _isDarkTheme.value ?: isSystemTheme

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }
}
