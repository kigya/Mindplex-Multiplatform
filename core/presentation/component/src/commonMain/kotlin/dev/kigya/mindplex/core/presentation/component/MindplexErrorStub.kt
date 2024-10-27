package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.component.theme.componentErrorStubButton
import dev.kigya.mindplex.core.presentation.component.theme.componentErrorStubButtonContainer
import dev.kigya.mindplex.core.presentation.component.theme.componentErrorStubButtonContent
import dev.kigya.mindplex.core.presentation.component.theme.componentErrorStubTitle
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import mindplex_multiplatform.core.presentation.component.generated.resources.Res
import mindplex_multiplatform.core.presentation.component.generated.resources.core_error_network_text
import mindplex_multiplatform.core.presentation.component.generated.resources.core_error_retry_button_text
import mindplex_multiplatform.core.presentation.component.generated.resources.core_error_unspecified_text
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal const val LOTTIE_WIDTH_PROPORTIONAL_DIVIDER = 6

enum class StubErrorType(
    val iconLottiePath: String,
    val text: StringResource,
) {
    NETWORK(
        iconLottiePath = "files/error_network.json",
        text = Res.string.core_error_network_text,
    ),
    UNSPECIFIED(
        iconLottiePath = "files/error_unspecified.json",
        text = Res.string.core_error_unspecified_text,
    ),
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MindplexErrorStub(
    modifier: Modifier = Modifier,
    stubErrorType: StubErrorType = StubErrorType.UNSPECIFIED,
    onRetryButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MindplexLottie(
            modifier = Modifier.size(getLottieErrorSize()),
            reader = { Res.readBytes(stubErrorType.iconLottiePath) },
        )
        MindplexSpacer(size = MindplexTheme.dimension.dp24)
        MindplexText(
            text = stringResource(stubErrorType.text),
            style = MindplexTheme.typography.componentErrorStubTitle,
            color = MindplexTheme.colorScheme.componentErrorStubTitle,
        )
        MindplexSpacer(size = MindplexTheme.dimension.dp24)
        MindplexButton(
            labelText = stringResource(Res.string.core_error_retry_button_text),
            textStyle = MindplexTheme.typography.componentErrorStubButton,
            contentColor = MindplexTheme.colorScheme.componentErrorStubButtonContent,
            containerColor = MindplexTheme.colorScheme.componentErrorStubButtonContainer,
            contentPadding = PaddingValues(
                vertical = MindplexTheme.dimension.dp16,
                horizontal = MindplexTheme.dimension.dp64,
            ),
            onClick = onRetryButtonClicked,
        )
    }
}

@Composable
internal expect fun getLottieErrorSize(): Dp
