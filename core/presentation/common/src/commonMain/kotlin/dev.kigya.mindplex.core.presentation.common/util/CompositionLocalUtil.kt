package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

inline fun <reified T> noCompositionLocalProvided(): T =
    error("No CompositionLocal provided for type: ${T::class.simpleName}")

inline fun <reified T> requiredCompositionLocalOf(): ProvidableCompositionLocal<T> =
    compositionLocalOf<T>(defaultFactory = ::noCompositionLocalProvided)
