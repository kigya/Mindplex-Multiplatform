package dev.kigya.mindplex.core.presentation.theme.activity

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("CompositionLocal LocalActivity not present")
}
