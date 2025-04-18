package dev.kigya.mindplex.feature.profile.presentation.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileSwitchBorder
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileSwitchThumb
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileSwitchTrack
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileThemeNameText
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_dark_theme
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_light_theme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ToggleChangeTheme(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    onThemeChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(
                ProfileTheme.dimension.dp16.value,
                Alignment.CenterHorizontally,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onThemeChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = ProfileTheme.colorScheme.profileSwitchThumb.value,
                uncheckedThumbColor = ProfileTheme.colorScheme.profileSwitchThumb.value,
                checkedTrackColor = ProfileTheme.colorScheme.profileSwitchTrack.value,
                uncheckedTrackColor = ProfileTheme.colorScheme.profileSwitchTrack.value,
                checkedBorderColor = ProfileTheme.colorScheme.profileSwitchBorder.value,
                uncheckedBorderColor = ProfileTheme.colorScheme.profileSwitchBorder.value,
            ),
        )

        MindplexText(
            value = stringResource(
                if (isDarkTheme) Res.string.profile_dark_theme else Res.string.profile_light_theme,
            ),
            color = ProfileTheme.colorScheme.profileThemeNameText,
            typography = ProfileTheme.typography.profileThemeNameText,
        )
    }
}
