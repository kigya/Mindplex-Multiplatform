package dev.kigya.mindplex.feature.home.presentation.block

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import dev.kigya.mindplex.core.presentation.uikit.MindplexLottie
import dev.kigya.mindplex.core.presentation.uikit.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.annotation.ExperimentalMindplexUiKitApi
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerBackground
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerDescription
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerTextShadow
import dev.kigya.mindplex.feature.home.presentation.ui.theme.HomeTheme.homeFactsPagerTitle
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_facts_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

private const val LOTTIE_ASPECT_RATIO = 2f

@OptIn(ExperimentalMindplexUiKitApi::class, ExperimentalResourceApi::class)
@Composable
internal fun FactsSection(
    state: HomeContract.State.FactsData,
    modifier: Modifier = Modifier,
) {
    MindplexPlaceholder(
        isLoading = state.areFactsLoading,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(LOTTIE_ASPECT_RATIO),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(LOTTIE_ASPECT_RATIO)
                .clip(HomeTheme.shape.rounding16.value)
                .background(HomeTheme.colorScheme.homeFactsPagerBackground.value),
        ) {
            MindplexLottie(
                reader = { Res.readBytes("files/home_facts.json") },
                modifier = Modifier.fillMaxSize(),
                shouldBeReversedOnRepeat = true,
                isRestartable = true,
                iterations = Int.MAX_VALUE,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HomeTheme.colorScheme.homeFactsPagerTextShadow.value.copy(alpha = 0.5f))
                    .padding(
                        horizontal = HomeTheme.dimension.dp16.value,
                        vertical = HomeTheme.dimension.dp36.value,
                    ),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start,
            ) {
                AnimatedVisibility(
                    visible = state.areFactsLoading.not(),
                    enter = slideInVertically(initialOffsetY = { -it / 2 }) + fadeIn(),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { -it / 2 }),
                ) {
                    MindplexText(
                        value = stringResource(Res.string.home_facts_title),
                        typography = HomeTheme.typography.homeFactsPagerTitle,
                        color = HomeTheme.colorScheme.homeFactsPagerTitle,
                        maxLines = 1,
                    )
                }

                AnimatedContent(
                    targetState = state.facts[state.currentIndex],
                    transitionSpec = {
                        val enter = slideInVertically { fullHeight -> fullHeight } + fadeIn()
                        val exit = slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
                        enter togetherWith exit
                    },
                ) { factText ->
                    MindplexText(
                        value = factText,
                        align = TextAlign.Start,
                        typography = HomeTheme.typography.homeFactsPagerDescription,
                        color = HomeTheme.colorScheme.homeFactsPagerDescription,
                    )
                }
            }
        }
    }
}
