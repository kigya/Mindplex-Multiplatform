@file:Suppress("Filename", "StringLiteralDuplication")

package dev.kigya.mindplex.feature.home.presentation.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.kigya.mindplex.core.presentation.uikit.preview.factory.PreviewScreensFactory
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.ui.HomeScreenContent
import kotlinx.collections.immutable.persistentListOf
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_pick_answer_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_random_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_true_or_false_mode

@PreviewScreensFactory
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParameterProvider::class)
    state: HomeContract.State,
) {
    HomeScreenContent(
        state = state,
        event = Lambda.noOpConsumer(),
    )
}

class HomeScreenPreviewParameterProvider : PreviewParameterProvider<HomeContract.State> {
    override val values = sequenceOf(
        HomeContract.State(
            headerData = HomeContract.State.HeaderData(
                userName = "Kian Fields",
                avatarUrl = "",
                isProfileNameLoading = false,
                isProfilePictureLoading = false,
            ),
            factsData = HomeContract.State.FactsData(
                areFactsLoading = false,
                facts = persistentListOf(
                    "The term arbitrary refers to the standard of review used by courts when reviewing a variety of " +
                        "decisions on appeal.",
                    "Some random fact to display in preview",
                    "Some random fact to display in preview",
                ),
            ),
            modesData = HomeContract.State.ModesData(
                areModesLoading = false,
                modes = persistentListOf(
                    HomeContract.State.ModesData.Mode(
                        type = HomeContract.State.ModesData.Mode.Type.PICK_ANSWER,
                        icon = Res.drawable.ic_pick_answer_mode,
                        title = Res.string.home_modes_pick_answer_title,
                        description = Res.string.home_modes_pick_answer_description,
                        shouldDisplayDelimiter = true,
                    ),
                    HomeContract.State.ModesData.Mode(
                        type = HomeContract.State.ModesData.Mode.Type.TRUE_OR_FALSE,
                        icon = Res.drawable.ic_true_or_false_mode,
                        title = Res.string.home_modes_true_of_false_title,
                        description = Res.string.home_modes_true_of_false_description,
                        shouldDisplayDelimiter = true,
                    ),
                    HomeContract.State.ModesData.Mode(
                        type = HomeContract.State.ModesData.Mode.Type.RANDOM,
                        icon = Res.drawable.ic_random_mode,
                        title = Res.string.home_modes_random_title,
                        description = Res.string.home_modes_random_description,
                        shouldDisplayDelimiter = false,
                    ),
                ),
            ),
        ),
        HomeContract.State(
            headerData = HomeContract.State.HeaderData(
                userName = "Kian Fields",
                avatarUrl = "",
                isProfileNameLoading = true,
                isProfilePictureLoading = true,
            ),
            factsData = HomeContract.State.FactsData(
                areFactsLoading = true,
                facts = persistentListOf(
                    "The term arbitrary refers to the standard of review used by courts when reviewing a variety of " +
                        "decisions on appeal.",
                    "Some random fact to display in preview",
                    "Some random fact to display in preview",
                ),
            ),
            modesData = HomeContract.State.ModesData(
                areModesLoading = true,
            ),
        ),
    )
}
