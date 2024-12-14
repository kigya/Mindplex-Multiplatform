package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import dev.kigya.mindplex.core.presentation.component.MindplexHorizontalPager
import dev.kigya.mindplex.core.presentation.component.MindplexIcon
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.theme.window.LocalWindow
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.PAGER_PLACEHOLDER_WIDTH_DIVIDER
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerDescription
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerTitle
import kotlinx.collections.immutable.ImmutableList
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_facts_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.im_facts
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FactsPager(
    modifier: Modifier = Modifier,
    state: HomeContract.State.FactsPagerData,
    pagerState: PagerState,
    facts: ImmutableList<String>,
) {
    val screenWidth = LocalWindow.current.width.toFloat()

    MindplexPlaceholder(
        modifier = modifier,
        size = Size(
            width = screenWidth,
            height = screenWidth / PAGER_PLACEHOLDER_WIDTH_DIVIDER,
        ),
        isLoading = state.areFactsLoading,
    ) {
        MindplexHorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag("home_pager"),
            pagerState = pagerState,
            pageSpacing = HomeTheme.dimension.dp16,
            beyondViewportPageCount = pagerState.pageCount,
        ) { page ->
            val currentFact = remember(page) { facts[page] }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(HomeTheme.shape.rounding16)
                    .background(HomeTheme.colorScheme.homeFactsPagerBackground)
                    .padding(HomeTheme.dimension.dp16)
                    .testTag("facts_page_$page"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MindplexIcon(
                    drawableResource = Res.drawable.im_facts,
                )

                MindplexSpacer(size = HomeTheme.dimension.dp16)

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    MindplexText(
                        text = stringResource(Res.string.home_facts_title),
                        style = HomeTheme.typography.homeFactsPagerTitle,
                        color = HomeTheme.colorScheme.homeFactsPagerTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    MindplexSpacer(size = HomeTheme.dimension.dp16)

                    MindplexText(
                        modifier = Modifier.weight(1f),
                        text = currentFact,
                        textAlign = TextAlign.Start,
                        style = HomeTheme.typography.homeFactsPagerDescription,
                        color = HomeTheme.colorScheme.homeFactsPagerDescription,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
