package dev.kigya.mindplex.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
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
        MindplexSpacer(size = MindplexSpacerSize.LARGE)
        MindplexText(
            text = stringResource(stubErrorType.text),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        MindplexSpacer(size = MindplexSpacerSize.LARGE)
        MindplexButton(
            labelText = stringResource(Res.string.core_error_retry_button_text),
            contentColor = MaterialTheme.colorScheme.primaryContainer,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentPadding = PaddingValues(
                vertical = MaterialTheme.spacing.medium,
                horizontal = MaterialTheme.spacing.giant,
            ),
            onClick = onRetryButtonClicked,
        )
    }
}

@Composable
internal expect fun getLottieErrorSize(): Dp
