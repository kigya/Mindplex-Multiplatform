package dev.kigya.mindplex.core.presentation.common.util

import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.theme.activity.LocalActivity

@Composable
actual fun SystemBarsColor(color: SystemBarsColor) {
    val activity = LocalActivity.current
    when (color) {
        SystemBarsColor.LIGHT -> activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )

        SystemBarsColor.DARK -> activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
        )

        SystemBarsColor.AUTO -> activity.enableEdgeToEdge()
    }
}
