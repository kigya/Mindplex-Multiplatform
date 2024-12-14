package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import dev.kigya.mindplex.core.presentation.common.extension.ShiftClickButtonState
import dev.kigya.mindplex.core.presentation.common.extension.shiftClickEffect
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexScaleIcon
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.MODES_PLACEHOLDER_WIDTH_DIVIDER
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesCardArrow
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesCardBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesCardDescription
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesCardTitle
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesDelimiter
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeModesIconBackground
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_mode_arrow
import org.jetbrains.compose.resources.stringResource

private const val HOME_MODES_ICON_INITIAL_SCALE = 1f
private const val HOME_MODES_ICON_TARGET_SCALE = 1.5f

@Composable
internal fun ModesCard(
    modifier: Modifier = Modifier,
    state: HomeContract.State.ModesData,
    event: (HomeContract.Event) -> Unit,
) {
    val screenWidth = LocalWindow.current.width.dp
    val screenHeight = LocalWindow.current.height.toFloat()
    val haptic = LocalHapticFeedback.current

    MindplexPlaceholder(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        isLoading = state.areModesLoading,
        size = Size(
            width = screenWidth.value,
            height = screenHeight / MODES_PLACEHOLDER_WIDTH_DIVIDER,
        ),
    ) {
        Card(
            modifier = modifier,
            shape = HomeTheme.shape.rounding16,
            colors = CardDefaults.cardColors(
                containerColor = HomeTheme.colorScheme.homeModesDelimiter,
            ),
        ) {
            state.modes.fastForEachIndexed { index, mode ->
                if (state.areModesLoading.not()) {
                    mode.icon?.let { iconRes ->
                        Surface(
                            modifier = Modifier.shiftClickEffect(
                                onChangeState = { buttonState ->
                                    performClickHapticFeedback(haptic)
                                    event(
                                        HomeContract.Event.OnModeClickStateChanged(
                                            index = index,
                                            shouldScaleIcon = buttonState == ShiftClickButtonState.Pressed,
                                        ),
                                    )
                                },
                                onClick = { event(HomeContract.Event.OnModeClicked(mode.type)) },
                            ),
                            color = HomeTheme.colorScheme.homeModesCardBackground,
                        ) {
                            Row(
                                modifier = Modifier.padding(all = HomeTheme.dimension.dp16),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(HomeTheme.shape.rounding8)
                                        .background(HomeTheme.colorScheme.homeModesIconBackground),
                                    contentAlignment = Alignment.BottomCenter,
                                ) {
                                    MindplexIcon(
                                        drawableResource = iconRes,
                                    )
                                }

                                MindplexSpacer(size = HomeTheme.dimension.dp16)

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    mode.title?.let { titleRes ->
                                        MindplexText(
                                            text = stringResource(titleRes),
                                            style = HomeTheme.typography.homeModesCardTitle,
                                            color = HomeTheme.colorScheme.homeModesCardTitle,
                                            textAlign = TextAlign.Start,
                                        )
                                    }

                                    MindplexSpacer(size = HomeTheme.dimension.dp12)

                                    mode.description?.let { descriptionRes ->
                                        MindplexText(
                                            text = stringResource(descriptionRes),
                                            style = HomeTheme.typography.homeModesCardDescription,
                                            color = HomeTheme.colorScheme.homeModesCardDescription,
                                            textAlign = TextAlign.Start,
                                        )
                                    }
                                }

                                MindplexScaleIcon(
                                    scale = if (mode.shouldScaleIcon) {
                                        HOME_MODES_ICON_TARGET_SCALE
                                    } else {
                                        HOME_MODES_ICON_INITIAL_SCALE
                                    },
                                    drawableResource = Res.drawable.ic_mode_arrow,
                                    tintColor = HomeTheme.colorScheme.homeModesCardArrow,
                                )
                            }
                        }
                        if (mode.shouldDisplayDelimiter) {
                            MindplexSpacer(
                                modifier = Modifier.background(HomeTheme.colorScheme.homeModesDelimiter),
                                size = HomeTheme.dimension.dp1,
                            )
                        }
                    }
                }
            }
        }
    }
}
