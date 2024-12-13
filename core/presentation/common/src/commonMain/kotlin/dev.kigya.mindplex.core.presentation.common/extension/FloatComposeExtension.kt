package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Stable
infix fun Float.by(density: Density) = with(density) { this@by.toDp() }

@Stable
infix fun Dp.by(density: Density): Float = with(density) { this@by.toPx() }

@Stable
infix fun TextUnit.by(density: Density): Float = with(density) { this@by.toPx() }

@Stable
@Composable
fun Dp.toPx(): Float = by(LocalDensity.current)

@Stable
@Composable
fun Float.toDp() = by(LocalDensity.current)

@Stable
@Composable
fun TextUnit.toPx(): Float = by(LocalDensity.current)
