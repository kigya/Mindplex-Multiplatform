package dev.kigya.mindplex.feature.game.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileNetworkRepositoryContract
import dev.kigya.mindplex.feature.game.domain.model.ValidationType
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.outcome.getOrNull
import kotlinx.coroutines.flow.first

class UpdateScoreUseCase(
    private val profileDatabaseRepositoryContract: UserProfileDatabaseRepositoryContract,
    private val profileNetworkRepositoryContract: UserProfileNetworkRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Unit, ValidationType>() {

    override suspend operator fun invoke(params: ValidationType) {
        val token = signInPreferencesRepositoryContract.userId.first().orEmpty()
        val currentScore =
            profileDatabaseRepositoryContract.getUserScore(token).getOrNull() ?: return

        val newScore = when (params) {
            ValidationType.CORRECT -> currentScore + CORRECT_REWARD
            ValidationType.INCORRECT -> currentScore + INCORRECT_REWARD
            ValidationType.NO_ANSWER -> currentScore + NO_REWARD
            ValidationType.NEUTRAL -> return
        }

        profileDatabaseRepositoryContract.saveUserScore(token, newScore)
        profileNetworkRepositoryContract.updateUserScore(token, newScore)
    }

    private companion object {
        const val CORRECT_REWARD = 2
        const val INCORRECT_REWARD = 1
        const val NO_REWARD = 0
    }
}
