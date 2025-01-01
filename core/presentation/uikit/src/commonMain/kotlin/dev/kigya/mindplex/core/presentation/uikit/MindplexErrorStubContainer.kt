package dev.kigya.mindplex.core.presentation.uikit

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.theme.MindplexDsToken
import dev.kigya.mindplex.core.presentation.uikit.theme.UiKitTheme
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse

@Composable
fun MindplexErrorStubContainer(
    background: MindplexDsToken<Color>,
    stubErrorType: StubErrorType?,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    onRetryButtonClicked: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background.value)
            .statusBarsPadding()
            .padding(
                horizontal = UiKitTheme.dimension.dp24.value,
                vertical = UiKitTheme.dimension.dp16.value,
            ),
    ) {
        AnimatedContent(
            targetState = stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = verticalArrangement,
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier.testTag("error_stub"),
                            stubErrorType = type,
                            onRetryButtonClicked = onRetryButtonClicked,
                        )
                    },
                    ifAbsent = { content() },
                )
            }
        }
    }
}
