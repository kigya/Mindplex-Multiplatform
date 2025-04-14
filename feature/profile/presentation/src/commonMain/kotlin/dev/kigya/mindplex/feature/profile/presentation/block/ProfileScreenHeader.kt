package dev.kigya.mindplex.feature.profile.presentation.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexIconButton
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextAnimation
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileIcons
import dev.kigya.mindplex.feature.profile.presentation.ui.theme.ProfileTheme.profileNameText
import mindplex_multiplatform.feature.profile.presentation.generated.resources.Res
import mindplex_multiplatform.feature.profile.presentation.generated.resources.ic_log_out
import mindplex_multiplatform.feature.profile.presentation.generated.resources.profile_name
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProfileScreenHeader(
    modifier: Modifier = Modifier,
    event: (ProfileContract.Event) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        MindplexText(
            value = stringResource(Res.string.profile_name),
            color = ProfileTheme.colorScheme.profileNameText,
            typography = ProfileTheme.typography.profileNameText,
            animation = MindplexTextAnimation.Typewriter,
        )

        MindplexIconButton(
            resource = Res.drawable.ic_log_out,
            color = ProfileTheme.colorScheme.profileIcons,
            onClick = { event(ProfileContract.Event.GoToRegistration) },
        )
    }
}
