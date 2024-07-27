package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun getScreenWidth() = LocalConfiguration.current.screenWidthDp

@Composable
actual fun getScreenHeight() = LocalConfiguration.current.screenHeightDp
