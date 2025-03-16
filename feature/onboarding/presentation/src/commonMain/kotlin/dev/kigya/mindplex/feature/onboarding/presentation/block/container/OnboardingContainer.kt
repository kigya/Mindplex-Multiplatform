package dev.kigya.mindplex.feature.onboarding.presentation.block.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme
import dev.kigya.mindplex.feature.onboarding.presentation.ui.theme.OnboardingTheme.onboardingBackground

@Composable
internal fun OnboardingContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(OnboardingTheme.colorScheme.onboardingBackground.value)
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(vertical = OnboardingTheme.dimension.dp24.value)
        .testTag("onboarding_content"),
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
)
