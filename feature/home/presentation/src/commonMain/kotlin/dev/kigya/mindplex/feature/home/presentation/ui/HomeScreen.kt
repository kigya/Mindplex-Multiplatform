package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(contract: HomeContract) {
    val (state, event, effect) = use(contract)

    HomeScreenContent(
        state = state,
        event = event,
        effect = effect,
    )
}

@Composable
@VisibleForTesting
@Suppress("UnusedParameter")
internal fun HomeScreenContent(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    effect: StableFlow<HomeContract.Effect>,
) {
    LaunchedEffectSaveable(Unit) { event(HomeContract.Event.OnFirstLaunch) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.medium,
            ),
    ) {
        HomeScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            name = state.userName,
            avatarUrl = state.avatarUrl,
            isProfileLoading = state.isProfileLoading,
        )
    }
}

@Composable
private fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String?,
    isProfileLoading: Boolean,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            MindplexPlaceholder(isLoading = isProfileLoading) {
                MindplexText(
                    text = stringResource(Res.string.home_welcome_back),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            MindplexSpacer(size = MindplexSpacerSize.EXTRA_SMALL)
            MindplexPlaceholder(
                isLoading = isProfileLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MaterialTheme.typography.headlineMedium,
            ) {
                MindplexText(
                    text = name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
        MindplexPlaceholder(
            isLoading = isProfileLoading,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(MaterialTheme.spacing.extraExtraLarge)
                    .clip(CircleShape)
                    .shimmer(),
                model = avatarUrl,
                contentDescription = null,
                onError = {
                },
                onLoading = {
                },
                onSuccess = {
                },
            )
        }
    }
}
