package dev.kigya.mindplex.feature.splash.presentation.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.feature.splash.presentation.ui.theme.SplashTheme
import dev.kigya.mindplex.feature.splash.presentation.ui.theme.SplashTheme.splashBackground

@Composable
internal fun SplashContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(SplashTheme.colorScheme.splashBackground.value),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
)
