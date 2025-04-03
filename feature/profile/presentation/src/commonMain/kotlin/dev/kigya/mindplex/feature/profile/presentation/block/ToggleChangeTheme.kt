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
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.switchBorder
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.switchThumb
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.switchTrack
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.themeNameText
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.dark_theme
import mindplex_multiplatform.feature.profile.presentation.generated.resources.light_theme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ToggleChangeTheme(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
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
                checkedThumbColor = ProfileTheme.colorScheme.switchThumb.value,
                uncheckedThumbColor = ProfileTheme.colorScheme.switchThumb.value,
                checkedTrackColor = ProfileTheme.colorScheme.switchTrack.value,
                uncheckedTrackColor = ProfileTheme.colorScheme.switchTrack.value,
                checkedBorderColor = ProfileTheme.colorScheme.switchBorder.value,
                uncheckedBorderColor = ProfileTheme.colorScheme.switchBorder.value,
            ),
        )

        MindplexText(
            value = if (isDarkTheme) {
                stringResource(Res.string.dark_theme)
            } else {
                stringResource(Res.string.light_theme)
            },
            color = ProfileTheme.colorScheme.themeNameText,
            typography = ProfileTheme.typography.themeNameText,
        )
    }
}
