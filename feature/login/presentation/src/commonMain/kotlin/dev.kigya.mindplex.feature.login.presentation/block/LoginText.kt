package dev.kigya.mindplex.feature.login.presentation.block

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme
import dev.kigya.mindplex.feature.login.presentation.ui.theme.LoginTheme.loginTitle
import mindplex_multiplatform.feature.login.presentation.generated.resources.Res
import mindplex_multiplatform.feature.login.presentation.generated.resources.login_welcome_to_mindplex
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginText() {
    MindplexText(
        value = stringResource(Res.string.login_welcome_to_mindplex),
        color = LoginTheme.colorScheme.loginTitle,
        typography = LoginTheme.typography.loginTitle,
    )
}
