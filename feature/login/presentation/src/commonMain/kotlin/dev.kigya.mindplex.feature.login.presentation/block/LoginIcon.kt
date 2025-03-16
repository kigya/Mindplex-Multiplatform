package dev.kigya.mindplex.feature.login.presentation.block

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginMindplexIcon
import mindplex_multiplatform.feature.login.presentation.generated.resources.Res
import mindplex_multiplatform.feature.login.presentation.generated.resources.ic_mindplex

@Composable
internal fun LoginIcon() {
    MindplexIcon(
        resource = Res.drawable.ic_mindplex,
        color = LoginTheme.colorScheme.loginMindplexIcon,
    )
}
