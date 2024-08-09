package dev.kigya.mindplex.core.presentation.theme.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth() = LocalWindowInfo.current.containerSize.width

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenHeight() = LocalWindowInfo.current.containerSize.height
