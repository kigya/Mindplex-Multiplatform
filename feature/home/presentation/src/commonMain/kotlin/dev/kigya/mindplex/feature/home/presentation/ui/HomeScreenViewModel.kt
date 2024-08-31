package dev.kigya.mindplex.feature.home.presentation.ui

import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.core.util.dsl.parZipOrAccumulate
import dev.kigya.mindplex.core.util.extension.update
import dev.kigya.mindplex.feature.home.domain.usecase.GetFactsUseCase
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.mapper.toUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
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

class HomeScreenViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFactsUseCase: GetFactsUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(HomeContract.State()), HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        event.run {
            when (this) {
                is HomeContract.Event.OnFirstLaunch -> handleFirstLaunch()
                is HomeContract.Event.OnProfilePictureLoaded -> handleProfilePictureLoading()
                is HomeContract.Event.OnProfilePictureErrorReceived -> handleProfilePictureError()
                is HomeContract.Event.OnErrorStubClicked -> handleErrorStubClick()
                is HomeContract.Event.OnModeClicked -> handleModeClick()
                is HomeContract.Event.OnModeClickStateChanged -> handleModeClickStateChange()
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
                        pagerData = pagerData.copy(
                            areFactsLoading = false,
                            facts = facts.toUi(),
                        ),
                        modesData = modesData.copy(
                            areModesLoading = false,
                            modes = getModes(),
                        ),
                    )
                }
                launch { enableAutoScrollEffect() }
            },
            onError = { errorList ->
                updateState {
                    copy(
                        stubErrorType = errorList.toStubErrorType(),
                        headerData = headerData.copy(
                            isProfileNameLoading = false,
                            isProfilePictureLoading = false,
                        ),
                        pagerData = pagerData.copy(
                            areFactsLoading = false,
                        ),
                    )
                }
            },
        )
    }

    private fun getModes() = persistentListOf(
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
    )

    private suspend fun enableAutoScrollEffect() {
        while (true) {
            delay(PAGER_AUTOSCROLL_DELAY)
            sendEffect(HomeContract.Effect.ScrollToNextPage)
        }
    }

    @Suppress("ForbiddenComment")
    private fun HomeContract.Event.OnModeClicked.handleModeClick() {
        // TODO
    }

    private fun HomeContract.Event.OnModeClickStateChanged.handleModeClickStateChange() {
        updateState {
            copy(
                modesData = modesData.copy(
                    modes = modesData.modes.update(index) { mode ->
                        mode.copy(shouldScaleIcon = shouldScaleIcon)
                    },
                ),
            )
        }
    }

    private companion object {
        const val FACTS_AMOUNT = 3
        const val PAGER_AUTOSCROLL_DELAY = 5000L
    }
}
