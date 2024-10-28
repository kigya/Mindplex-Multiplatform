package dev.kigya.mindplex.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import dev.kigya.mindplex.core.presentation.component.MindplexPlaceholder
import dev.kigya.mindplex.core.presentation.component.MindplexSpacer
import dev.kigya.mindplex.core.presentation.component.MindplexText
import dev.kigya.mindplex.core.presentation.component.MindplexTypewriterText
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeProfileNameText
import dev.kigya.mindplex.feature.home.presentation.ui.theme.homeWelcomeBackText
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_user_name_preview
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_welcome_back
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_profile_fallback
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    state: HomeContract.State.HeaderData,
    event: (HomeContract.Event) -> Unit,
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
            MindplexPlaceholder(isLoading = state.isProfileNameLoading) {
                MindplexText(
                    text = stringResource(Res.string.home_welcome_back),
                    color = MindplexTheme.colorScheme.homeWelcomeBackText,
                    style = MindplexTheme.typography.homeWelcomeBackText,
                )
            }

            MindplexSpacer(size = MindplexTheme.dimension.dp8)

            MindplexPlaceholder(
                isLoading = state.isProfileNameLoading,
                textToMeasure = stringResource(Res.string.home_user_name_preview),
                textStyle = MindplexTheme.typography.homeProfileNameText,
            ) {
                MindplexTypewriterText(text = state.userName)
            }
        }

        MindplexPlaceholder(isLoading = state.isProfilePictureLoading && state.isProfileNameLoading) {
            AsyncImage(
                modifier = Modifier
                    .size(MindplexTheme.dimension.dp48)
                    .clip(CircleShape),
                model = state.avatarUrl,
                contentDescription = null,
                error = painterResource(Res.drawable.ic_profile_fallback),
                fallback = painterResource(
                    resource = Res.drawable.ic_profile_fallback,
                ).takeIf { state.isProfileNameLoading.not() },
                onError = { event(HomeContract.Event.OnProfilePictureErrorReceived) },
                onSuccess = { event(HomeContract.Event.OnProfilePictureLoaded) },
            )
        }
    }
}
