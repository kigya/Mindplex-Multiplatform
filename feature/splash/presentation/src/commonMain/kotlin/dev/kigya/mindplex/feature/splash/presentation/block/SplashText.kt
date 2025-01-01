package dev.kigya.mindplex.feature.splash.presentation.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.splash.presentation.contract.SplashContract
import dev.kigya.mindplex.feature.splash.presentation.ui.theme.SplashTheme
import dev.kigya.mindplex.feature.splash.presentation.ui.theme.SplashTheme.splashHeader
import dev.kigya.mindplex.feature.splash.presentation.ui.theme.SplashTheme.splashTitle
import mindplex_multiplatform.feature.splash.presentation.generated.resources.Res
import mindplex_multiplatform.feature.splash.presentation.generated.resources.splash_title
import org.jetbrains.compose.resources.stringResource

private const val ANIMATION_DURATION_MILLIS = 700

@Composable
internal fun SplashText(state: SplashContract.State) = AnimatedVisibility(
    visible = state.shouldDisplayText,
    enter = fadeIn(tween(ANIMATION_DURATION_MILLIS)) + expandVertically(tween(ANIMATION_DURATION_MILLIS)),
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MindplexSpacer(size = SplashTheme.dimension.dp16)
        MindplexText(
            value = stringResource(Res.string.splash_title),
            color = SplashTheme.colorScheme.splashTitle,
            typography = SplashTheme.typography.splashHeader,
            modifier = Modifier.testTag("splash_text"),
        )
    }
}
