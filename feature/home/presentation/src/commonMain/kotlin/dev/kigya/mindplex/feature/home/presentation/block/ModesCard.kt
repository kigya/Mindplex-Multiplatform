package dev.kigya.mindplex.feature.home.presentation.block

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
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.fastForEachIndexed
import dev.kigya.mindplex.core.presentation.common.extension.ShiftClickButtonState
import dev.kigya.mindplex.core.presentation.common.extension.shiftClickEffect
import dev.kigya.mindplex.core.presentation.common.util.performClickHapticFeedback
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexMeasurablePlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
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
    state: HomeContract.State.ModesData,
    event: (HomeContract.Event) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    MindplexMeasurablePlaceholder(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        isLoading = state.areModesLoading,
    ) {
        ModesCardContent(
            state = state,
            haptic = haptic,
            event = event,
        )
    }
}

@Composable
private fun ModesCardContent(
    state: HomeContract.State.ModesData,
    haptic: HapticFeedback,
    event: (HomeContract.Event) -> Unit,
) {
    Card(
        shape = HomeTheme.shape.rounding16.value,
        colors = CardDefaults.cardColors(
            containerColor = HomeTheme.colorScheme.homeModesDelimiter.value,
        ),
    ) {
        state.modes.fastForEachIndexed { index, mode ->
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
                    color = HomeTheme.colorScheme.homeModesCardBackground.value,
                ) {
                    Row(
                        modifier = Modifier.padding(all = HomeTheme.dimension.dp16.value),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(HomeTheme.shape.rounding8.value)
                                .background(HomeTheme.colorScheme.homeModesIconBackground.value),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            MindplexIcon(
                                resource = iconRes,
                            )
                        }

                        MindplexSpacer(size = HomeTheme.dimension.dp16)

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            mode.title?.let { titleRes ->
                                MindplexText(
                                    value = stringResource(titleRes),
                                    typography = HomeTheme.typography.homeModesCardTitle,
                                    color = HomeTheme.colorScheme.homeModesCardTitle,
                                    align = TextAlign.Start,
                                )
                            }

                            MindplexSpacer(size = HomeTheme.dimension.dp12)

                            mode.description?.let { descriptionRes ->
                                MindplexText(
                                    value = stringResource(descriptionRes),
                                    typography = HomeTheme.typography.homeModesCardDescription,
                                    color = HomeTheme.colorScheme.homeModesCardDescription,
                                    align = TextAlign.Start,
                                )
                            }
                        }

                        MindplexIcon(
                            resource = Res.drawable.ic_mode_arrow,
                            color = HomeTheme.colorScheme.homeModesCardArrow,
                            scale = {
                                if (mode.shouldScaleIcon) {
                                    HOME_MODES_ICON_TARGET_SCALE
                                } else {
                                    HOME_MODES_ICON_INITIAL_SCALE
                                }
                            },
                        )
                    }
                }
                if (mode.shouldDisplayDelimiter) {
                    MindplexSpacer(
                        modifier = Modifier.background(HomeTheme.colorScheme.homeModesDelimiter.value),
                        size = HomeTheme.dimension.dp1,
                    )
                }
            }
        }
    }
}
