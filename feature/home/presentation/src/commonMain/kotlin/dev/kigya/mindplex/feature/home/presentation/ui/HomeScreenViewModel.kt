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
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Companion.FACTS_AMOUNT
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State.CategorySelectionData.CategoryData
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.State.CategorySelectionData.DifficultyChipData
import dev.kigya.mindplex.feature.home.presentation.mapper.FactsPresentationMapper
import dev.kigya.mindplex.feature.home.presentation.mapper.FactsPresentationMapper.mappedBy
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract
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

class HomeScreenViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFactsUseCase: GetFactsUseCase,
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = HomeContract.State(),
),
    HomeContract {

    override fun executeStartAction() = withUseCaseScope {
        fetchScreenData()
    }

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        event.run {
            when (this) {
                is HomeContract.Event.OnStopLifecycleEventReceived -> handleProfilePictureLoading()
                is HomeContract.Event.OnProfilePictureLoaded -> handleProfilePictureLoading()
                is HomeContract.Event.OnProfilePictureErrorReceived -> handleProfilePictureError()
                is HomeContract.Event.OnErrorStubClicked -> handleErrorStubClick()
                is HomeContract.Event.OnModeClicked -> handleModeClick()
                is HomeContract.Event.OnModeClickStateChanged -> handleModeClickStateChange()
                is HomeContract.Event.OnPopupDismissed -> handlePopupDismiss()
                is HomeContract.Event.OnCategoryClicked -> handleCategoryClick()
                is HomeContract.Event.OnDifficultyClicked -> handleDifficultyClick()
            }
        }
    }

    private fun handleProfilePictureLoading() = updateState {
        copy(headerData = headerData.copy(isProfilePictureLoading = false))
    }

    private fun handleProfilePictureError() = updateState {
        copy(headerData = headerData.copy(isProfilePictureLoading = false))
    }

    private suspend fun handleErrorStubClick() = fetchScreenData()

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { HomeContract.State() }

        parZipOrAccumulate(
            fa = { getUserProfileUseCase(None) },
            fb = { getFactsUseCase(GetFactsUseCase.Params(FACTS_AMOUNT)) },
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
                        factsData = factsData.copy(
                            areFactsLoading = false,
                            facts = facts mappedBy FactsPresentationMapper,
                        ),
                        modesData = modesData.copy(
                            areModesLoading = false,
                        ),
                        categorySelectionData = categorySelectionData.copy(
                            categories = getCategories(),
                            difficulties = getDifficulties(),
                        ),
                    )
                }
                launch { enableFactsAutoChangeEffect() }
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

    private suspend fun enableFactsAutoChangeEffect() {
        while (true) {
            delay(FACTS_CHANGE_DELAY)
            updateState {
                copy(
                    factsData = factsData.copy(
                        currentIndex = (getState().factsData.currentIndex + 1) % FACTS_AMOUNT,
                    ),
                )
            }
        }
    }

    @Suppress("ForbiddenComment")
    private fun HomeContract.Event.OnModeClicked.handleModeClick() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                modeTitle = modesData.getTitleByType(type),
                shouldDisplayPopup = true,
            ),
        )
    }

    private fun HomeContract.Event.OnModeClickStateChanged.handleModeClickStateChange() =
        updateState {
            copy(
                modesData = modesData.copy(
                    modes = modesData.modes.update(index) { mode ->
                        mode.copy(shouldScaleIcon = shouldScaleIcon)
                    },
                ),
            )
        }

    private fun HomeContract.Event.OnPopupDismissed.handlePopupDismiss() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                shouldDisplayPopup = false,
                modeTitle = null,
            ),
        )
    }

    private suspend fun HomeContract.Event.OnCategoryClicked.handleCategoryClick() = updateState {
        copy(
            categorySelectionData = categorySelectionData.copy(
                shouldDisplayPopup = false,
                modeTitle = null,
            ),
        )
    }.also { navigatorContract.navigateTo(route = ScreenRoute.Game) }

    private fun HomeContract.Event.OnDifficultyClicked.handleDifficultyClick() = updateState {
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
        const val FACTS_CHANGE_DELAY = 5000L
    }
}
