@file:Suppress("MagicNumber")

package dev.kigya.mindplex.feature.onboarding.presentation.block.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun RowScope.OnboardingControlsContainerLandscape(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = Modifier
        .weight(0.4f)
        .fillMaxHeight(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
)
