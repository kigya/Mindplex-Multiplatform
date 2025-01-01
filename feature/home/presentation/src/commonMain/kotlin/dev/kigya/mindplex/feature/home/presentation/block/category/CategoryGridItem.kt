package dev.kigya.mindplex.feature.home.presentation.block.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dev.kigya.mindplex.core.presentation.uikit.MindplexIcon
import dev.kigya.mindplex.core.presentation.uikit.MindplexSpacer
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.categorySelectionItem
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.categorySelectionRipple
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CategoryGridItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    name: StringResource,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = HomeTheme.dimension.dp8.value)
            .clip(shape = HomeTheme.shape.rounding16.value)
            .clickable(
                onClick = onClick,
                interactionSource = remember(::MutableInteractionSource),
                indication = ripple(color = HomeTheme.colorScheme.categorySelectionRipple.value),
            )
            .padding(vertical = HomeTheme.dimension.dp8.value)
            .width(intrinsicSize = IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        MindplexIcon(
            resource = icon,
            modifier = Modifier.size(HomeTheme.dimension.dp64.value),
        )

        MindplexSpacer(size = HomeTheme.dimension.dp16)

        MindplexText(
            value = stringResource(name),
            typography = HomeTheme.typography.categorySelectionItem,
            color = HomeTheme.colorScheme.categorySelectionItem,
            modifier = Modifier.width(IntrinsicSize.Min),
        )
    }
}
