package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun performClickHapticFeedback() {
    val hapticFeedback = LocalHapticFeedback.current
    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
}

fun performClickHapticFeedback(hapticFeedback: HapticFeedback) =
    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
