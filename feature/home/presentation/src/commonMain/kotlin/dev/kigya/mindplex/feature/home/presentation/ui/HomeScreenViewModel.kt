package dev.kigya.mindplex.feature.home.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.core.util.dsl.parZipOrAccumulate
import dev.kigya.mindplex.core.util.extension.mapPersistent
import dev.kigya.mindplex.core.util.extension.update
import dev.kigya.mindplex.feature.home.domain.usecase.GetFactsUseCase
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Effect
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Event
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State.CategorySelectionData.CategoryData
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State.CategorySelectionData.DifficultyChipData
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State.ModesData
import dev.kigya.mindplex.feature.home.presentation.mapper.toUi
import dev.kigya.mindplex.navigation.navigator.navigator.AppNavigatorContract
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_difficulty_easy
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_difficulty_hard
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_difficulty_medium
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_pick_answer_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_random_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_true_or_false_mode

class HomeScreenViewModel(
    private val navigatorContract: AppNavigatorContract,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFactsUseCase: GetFactsUseCase,
) : BaseViewModel<State, Effect>(State()), HomeContract {

    override fun handleEvent(event: Event) = withUseCaseScope {
        event.run {
            when (this) {
                is Event.OnFirstLaunch -> handleFirstLaunch()
                is Event.OnProfilePictureLoaded -> handleProfilePictureLoading()
                is Event.OnProfilePictureErrorReceived -> handleProfilePictureError()
                is Event.OnErrorStubClicked -> handleErrorStubClick()
                is Event.OnModeClicked -> handleModeClick()
                is Event.OnModeClickStateChanged -> handleModeClickStateChange()
                is Event.OnPopupDismissed -> handlePopupDismiss()
                is Event.OnCategoryClicked -> handleCategoryClick()
                is Event.OnDifficultyClicked -> handleDifficultyClick()
            }
        }
    }

    private suspend fun handleFirstLaunch() = fetchScreenData()

    private fun handleProfilePictureLoading() = updateState {
        copy(headerData = headerData.copy(isProfilePictureLoading = false))
    }

    private fun handleProfilePictureError() = updateState {
        copy(headerData = headerData.copy(isProfilePictureLoading = false))
    }

    private suspend fun handleErrorStubClick() = fetchScreenData()

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { State() }

        parZipOrAccumulate(
            fa = { getUserProfileUseCase(None) },
            fb = { getFactsUseCase(GetFactsUseCase.Params(HomeContract.FACTS_AMOUNT)) },
            onSuccess = { profile, facts ->
                updateState {
                    copy(
                        stubErrorType = null,
                        headerData = headerData.copy(
                            userName = profile.displayName,
                            avatarUrl = profile.profilePictureUrl,
                            isProfileNameLoading = false,
                            isProfilePictureLoading = false,
                        ),
                        factsPagerData = factsPagerData.copy(
                            areFactsLoading = false,
                            facts = facts.toUi(),
                        ),
                        modesData = modesData.copy(
                            areModesLoading = false,
                            modes = getModes(),
                        ),
                        categorySelectionData = categorySelectionData.copy(
                            categories = getCategories(),
                            difficulties = getDifficulties(),
                        ),
                    )
                }
                launch { enableAutoScrollEffect() }
            },
            onError = { errorList ->
                updateState {
                    copy(
                        stubErrorType = errorList.toStubErrorType(),
                    )
                }
            },
        )
    }

    private fun getModes() = persistentListOf(
        ModesData.Mode(
            type = ModesData.Mode.Type.PICK_ANSWER,
            icon = Res.drawable.ic_pick_answer_mode,
            title = Res.string.home_modes_pick_answer_title,
            description = Res.string.home_modes_pick_answer_description,
            shouldDisplayDelimiter = true,
        ),
        ModesData.Mode(
            type = ModesData.Mode.Type.TRUE_OR_FALSE,
            icon = Res.drawable.ic_true_or_false_mode,
            title = Res.string.home_modes_true_of_false_title,
            description = Res.string.home_modes_true_of_false_description,
            shouldDisplayDelimiter = true,
        ),
        ModesData.Mode(
            type = ModesData.Mode.Type.RANDOM,
            icon = Res.drawable.ic_random_mode,
            title = Res.string.home_modes_random_title,
            description = Res.string.home_modes_random_description,
            shouldDisplayDelimiter = false,
        ),
    )

    private suspend fun enableAutoScrollEffect() {
        while (true) {
            delay(PAGER_AUTOSCROLL_DELAY)
            sendEffect(Effect.ScrollFactsToNextPage)
        }
    }

    @Suppress("ForbiddenComment")
    private fun Event.OnModeClicked.handleModeClick() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                modeTitle = modesData.getTitleByType(type),
                shouldDisplayPopup = true,
            ),
        )
    }

    private fun Event.OnModeClickStateChanged.handleModeClickStateChange() = updateState {
        copy(
            modesData = modesData.copy(
                modes = modesData.modes.update(index) { mode ->
                    mode.copy(shouldScaleIcon = shouldScaleIcon)
                },
            ),
        )
    }

    private fun Event.OnPopupDismissed.handlePopupDismiss() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                shouldDisplayPopup = false,
                modeTitle = null,
            ),
        )
    }

    private suspend fun Event.OnCategoryClicked.handleCategoryClick() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                shouldDisplayPopup = false,
                modeTitle = null,
            ),
        )
    }.also { navigatorContract.navigateTo(route = ScreenRoute.Game) }

    private fun Event.OnDifficultyClicked.handleDifficultyClick() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                difficulties = categorySelectionData.difficulties.mapPersistent { difficulty ->
                    difficulty.copy(isSelected = difficulty == selectedChip)
                },
            ),
        )
    }

    private fun getCategories() = CategoryData.entries.toImmutableList()

    private fun getDifficulties() = persistentListOf(
        DifficultyChipData(
            textResource = Res.string.home_categories_difficulty_easy,
            isSelected = true,
        ),
        DifficultyChipData(
            textResource = Res.string.home_categories_difficulty_medium,
            isSelected = false,
        ),
        DifficultyChipData(
            textResource = Res.string.home_categories_difficulty_hard,
            isSelected = false,
        ),
    )

    private companion object {
        const val PAGER_AUTOSCROLL_DELAY = 5000L
    }
}
