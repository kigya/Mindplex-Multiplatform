package dev.kigya.mindplex.feature.home.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.testTag
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.common.util.LaunchedEffectSaveable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.fadeSlideScaleContentTransitionSpec
import dev.kigya.mindplex.core.presentation.component.MindplexErrorStub
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexSpacerSize
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.component.MindplexTypewriterText
import dev.kigya.mindplex.core.presentation.feature.effect.use
import dev.kigya.mindplex.core.presentation.theme.spacing.spacing
import dev.kigya.mindplex.core.util.dsl.ifPresentOrElse
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import org.jetbrains.compose.resources.painterResource
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.medium,
            ),
    ) {
        AnimatedContent(
            targetState = state.stubErrorType,
            transitionSpec = { fadeSlideScaleContentTransitionSpec() },
        ) { stubErrorType ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("home_section"),
            ) {
                stubErrorType.ifPresentOrElse(
                    ifPresent = { type ->
                        MindplexErrorStub(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("error_stub"),
                            stubErrorType = type,
                        ) { event(HomeContract.Event.OnErrorStubClicked) }
                    },
                    ifAbsent = {
                        HomeSection(
                            state = state,
                            event = event,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun HomeSection(state: HomeContract.State, event: (HomeContract.Event) -> Unit) {
    HomeScreenHeader(
        modifier = Modifier.fillMaxWidth(),
        event = event,
        name = state.userName,
        avatarUrl = state.avatarUrl,
        isProfileNameLoading = state.isProfileNameLoading,
        isProfilePictureLoading = state.isProfilePictureLoading,
    )
}

@Composable
private fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    event: (HomeContract.Event) -> Unit,
    name: String,
    avatarUrl: String?,
    isProfileNameLoading: Boolean,
    isProfilePictureLoading: Boolean,
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
            MindplexPlaceholder(isLoading = isProfileNameLoading) {
                MindplexText(
                    text = stringResource(Res.string.home_welcome_back),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            MindplexSpacer(size = MindplexSpacerSize.EXTRA_SMALL)
            MindplexPlaceholder(
                isLoading = isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MaterialTheme.typography.headlineMedium,
            ) {
                MindplexTypewriterText(text = name)
            }
        }
        MindplexPlaceholder(isLoading = isProfilePictureLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(MaterialTheme.spacing.extraExtraLarge)
                    .clip(CircleShape),
                model = avatarUrl,
                contentDescription = null,
                error = painterResource(Res.drawable.ic_profile_fallback),
                onError = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
            )
        }
    }
}
