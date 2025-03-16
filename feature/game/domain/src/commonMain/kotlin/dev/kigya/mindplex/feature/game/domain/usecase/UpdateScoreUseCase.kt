package dev.kigya.mindplex.feature.game.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.flow.first

class UpdateScoreUseCase(
    private val profileDatabaseRepositoryContract: UserProfileDatabaseRepositoryContract,
    private val profileNetworkRepositoryContract: UserProfileNetworkRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Unit, Boolean>() {

    override suspend operator fun invoke(params: Boolean) {
        val token = signInPreferencesRepositoryContract.userToken.first().orEmpty()
        val currentScore =
            profileDatabaseRepositoryContract.getUserScore(token).getOrNull() ?: return

        val newScore = if (params) {
            currentScore + CORRECT_REWARD
        } else {
            currentScore + INCORRECT_REWARD
        }

        profileDatabaseRepositoryContract.saveUserScore(token, newScore)
        profileNetworkRepositoryContract.updateUserScore(token, newScore)
    }

    private companion object {
        const val CORRECT_REWARD = 2
        const val INCORRECT_REWARD = 1
    }
}
