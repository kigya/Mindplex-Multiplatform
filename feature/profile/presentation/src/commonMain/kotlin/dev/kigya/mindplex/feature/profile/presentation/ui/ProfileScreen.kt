package dev.kigya.mindplex.feature.profile.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.common.util.MindplexAdaptiveContainer
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.uikit.MindplexErrorStubContainer
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.feature.profile.presentation.block.ProfileScreenHeader
import dev.kigya.mindplex.feature.profile.presentation.block.ProfileUserCard
import dev.kigya.mindplex.feature.profile.presentation.block.ToggleChangeTheme
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.feature.profile.presentation.ui.provider.ProfileCompositionLocalProvider
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileBackground

@Composable
fun ProfileScreen(contract: ProfileContract) {
    val (state, event, _) = use(contract)

    ProfileScreenContent(
        state = state,
        event = event,
    )
}

@Composable
@VisibleForTesting
internal fun ProfileScreenContent(
    state: ProfileContract.State,
    event: (ProfileContract.Event) -> Unit,
) = ProfileCompositionLocalProvider {
    MindplexErrorStubContainer(
        background = ProfileTheme.colorScheme.profileBackground,
        stubErrorType = state.stubErrorType,
        verticalArrangement = Arrangement.Top,
        onRetryButtonClicked = { event(ProfileContract.Event.OnErrorStubClicked) },
    ) {
        MindplexAdaptiveContainer(
            portrait = {
                ProfilePortraitSection(
                    state = state,
                    event = event,
                )
            },
            landscape = {
                ProfileLandscapeSection(
                    state = state,
                    event = event,
                )
            },
        )
    }
}

@Composable
private fun ColumnScope.ProfilePortraitSection(
    state: ProfileContract.State,
    event: (ProfileContract.Event) -> Unit,
) {
    ProfileScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        event = event,
    )

    MindplexSpacer(modifier = Modifier.width(ProfileTheme.dimension.dp64.value))

    ProfileUserCard(
        state = state.userProfile,
        isLoading = state.profileLoading,
    )

    state.isDarkTheme?.let { isDarkTheme ->
        ToggleChangeTheme(
            isDarkTheme = isDarkTheme,
            onThemeChange = { newValue -> event(ProfileContract.Event.OnThemeChanged(newValue)) },
        )
    }
}

@Composable
private fun ColumnScope.ProfileLandscapeSection(
    state: ProfileContract.State,
    event: (ProfileContract.Event) -> Unit,
) {
    ProfileScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        event = event,
    )

    MindplexSpacer(modifier = Modifier.width(ProfileTheme.dimension.dp64.value))

    ProfileUserCard(
        state = state.userProfile,
        isLoading = state.profileLoading,
    )

    state.isDarkTheme?.let { isDarkTheme ->
        ToggleChangeTheme(
            isDarkTheme = isDarkTheme,
            onThemeChange = { newValue -> event(ProfileContract.Event.OnThemeChanged(newValue)) },
        )
    }
}
