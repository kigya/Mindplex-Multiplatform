package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * [Figma](https://figmashort.link/MZMSFP)
 */
@Composable
fun RowScope.MindplexFillSpacer(modifier: Modifier = Modifier) = Spacer(modifier.weight(1f))

/**
 * [Figma](https://figmashort.link/MZMSFP)
 */
@Composable
fun ColumnScope.MindplexFillSpacer(modifier: Modifier = Modifier) = Spacer(modifier.weight(1f))
