package dev.kigya.mindplex.feature.home.presentation.ui

import arrow.core.Either.Companion.zipOrAccumulate
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.usecase.GetUserProfileUseCase
import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.core.presentation.feature.mapper.toStubErrorType
import dev.kigya.mindplex.feature.home.domain.usecase.GetFactsUseCase
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract
import dev.kigya.mindplex.feature.home.presentation.mapper.toUi
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class HomeScreenViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFactsUseCase: GetFactsUseCase,
) : BaseViewModel<HomeContract.State, HomeContract.Effect>(HomeContract.State()), HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        when (event) {
            is HomeContract.Event.OnFirstLaunch -> handleFirstLaunch()
            HomeContract.Event.OnProfilePictureLoaded -> handleProfilePictureLoaded()
            HomeContract.Event.OnProfilePictureErrorReceived -> handleProfilePictureErrorReceived()
            HomeContract.Event.OnErrorStubClicked -> handleOnErrorStubClicked()
        }
    }

    private suspend fun handleFirstLaunch() = fetchScreenData()

    private fun handleProfilePictureLoaded() = updateState {
        copy(isProfilePictureLoading = false)
    }

    private fun handleProfilePictureErrorReceived() = updateState {
        copy(isProfilePictureLoading = false)
    }

    private suspend fun handleOnErrorStubClicked() = fetchScreenData()

    private suspend fun fetchScreenData() = supervisorScope {
        updateState { HomeContract.State() }

        val profileDeferred = async { getUserProfileUseCase(None) }
        val factsDeferred = async { getFactsUseCase(GetFactsUseCase.Params(FACTS_AMOUNT)) }

        val profileResult = profileDeferred.await()
        val factsResult = factsDeferred.await()

        zipOrAccumulate(profileResult, factsResult) { profile, facts ->
            updateState {
                copy(
                    stubErrorType = null,
                    userName = profile.displayName,
                    avatarUrl = profile.profilePictureUrl,
                    isProfileNameLoading = false,
                    isProfilePictureLoading = false,
                    areFactsLoading = false,
                    facts = facts.toUi(),
                )
            }
        }.fold(
            ifLeft = { errorList ->
                updateState {
                    copy(
                        stubErrorType = errorList.toStubErrorType(),
                        isProfileNameLoading = false,
                        isProfilePictureLoading = false,
                        areFactsLoading = false,
                    )
                }
            },
            ifRight = { },
        )
    }

    private companion object {
        const val FACTS_AMOUNT = 3
    }
}
