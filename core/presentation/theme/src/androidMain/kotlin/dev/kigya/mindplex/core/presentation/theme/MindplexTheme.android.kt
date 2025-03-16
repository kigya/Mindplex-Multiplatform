package dev.kigya.mindplex.core.presentation.theme

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import dev.kigya.mindplex.core.presentation.theme.activity.LocalActivity

actual val platformCompositionValues: Array<ProvidedValue<*>>
    @Composable
    get() = arrayOf(LocalActivity provides LocalContext.current.componentActivity)

private val Context.componentActivity: ComponentActivity
    @Composable
    get() = when {
        this is ComponentActivity -> this
        LocalInspectionMode.current -> ComponentActivity()
        else -> error("No activity provided")
    }
