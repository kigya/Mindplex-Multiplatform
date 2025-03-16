package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentErrorStubButton
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentErrorStubButtonBackground
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentErrorStubButtonText
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme.componentErrorStubTitle
import mindplex_multiplatform.core.presentation.uikit.generated.resources.Res
import mindplex_multiplatform.core.presentation.uikit.generated.resources.core_error_network_text
import mindplex_multiplatform.core.presentation.uikit.generated.resources.core_error_retry_button_text
import mindplex_multiplatform.core.presentation.uikit.generated.resources.core_error_unspecified_text
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

        MindplexSpacer(size = UiKitTheme.dimension.dp24)

        MindplexText(
            value = stringResource(stubErrorType.text),
            typography = UiKitTheme.typography.componentErrorStubTitle,
            color = UiKitTheme.colorScheme.componentErrorStubTitle,
        )

        MindplexSpacer(size = UiKitTheme.dimension.dp24)

        MindplexButton(
            text = stringResource(Res.string.core_error_retry_button_text),
            backgroundColor = UiKitTheme.colorScheme.componentErrorStubButtonBackground,
            borderColor = UiKitTheme.colorScheme.componentErrorStubButtonBackground,
            textColor = UiKitTheme.colorScheme.componentErrorStubButtonText,
            textTypography = UiKitTheme.typography.componentErrorStubButton,
            verticalPadding = UiKitTheme.dimension.dp16,
            horizontalPadding = UiKitTheme.dimension.dp36,
            onClick = onRetryButtonClicked,
        )
    }
}

@Composable
internal expect fun getLottieErrorSize(): Dp
