package dev.kigya.mindplex.core.presentation.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.util.fastForEach
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

/**
 * SubcomposeLayout that [SubcomposeMeasureScope.subcompose]s [mainContent]
 * and gets total size of [mainContent] and passes this size to [dependentContent].
 * This layout passes exact size of content unlike
 * BoxWithConstraints which returns [Constraints] that doesn't match Composable dimensions under
 * some circumstances
 *
 * @param shouldPlaceMainContent when set to true places main content. Set this flag to false
 * when dimensions of content is required for inside [mainContent]. Just measure it then pass
 * its dimensions to any child composable
 *
 * @param mainContent Composable is used for calculating size and pass it
 * to Composables that depend on it
 *
 * @param dependentContent Composable requires dimensions of [mainContent] to set its size.
 * One example for this is overlay over Composable that should match [mainContent] size.
 *
 */
@Composable
fun DimensionSubcomposeLayout(
    modifier: Modifier = Modifier,
    shouldPlaceMainContent: Boolean = true,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (Size) -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier,
    ) { constraints: Constraints ->

        val mainPlaceables: ImmutableList<Placeable> = subcompose(SlotsEnum.Main, mainContent)
            .map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
            .toPersistentList()

        var maxWidth = 0
        var maxHeight = 0

        mainPlaceables.forEach { placeable: Placeable ->
            maxWidth += placeable.width
            maxHeight = placeable.height
        }

        val dependentPlaceables: ImmutableList<Placeable> = subcompose(SlotsEnum.Dependent) {
            dependentContent(Size(maxWidth.toFloat(), maxHeight.toFloat()))
        }.map { measurable: Measurable ->
            measurable.measure(constraints)
        }.toPersistentList()

        layout(maxWidth, maxHeight) {
            if (shouldPlaceMainContent) {
                mainPlaceables.fastForEach { placeable: Placeable ->
                    placeable.placeRelative(0, 0)
                }
            } else {
                dependentPlaceables.fastForEach { placeable: Placeable ->
                    placeable.placeRelative(0, 0)
                }
            }
        }
    }
}

enum class SlotsEnum { Main, Dependent }
