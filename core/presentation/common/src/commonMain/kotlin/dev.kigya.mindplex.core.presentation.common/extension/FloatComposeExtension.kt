package dev.kigya.mindplex.core.presentation.common.extension

import androidx.compose.ui.unit.Density

infix fun Float.by(density: Density) = with(density) { this@by.toDp() }
