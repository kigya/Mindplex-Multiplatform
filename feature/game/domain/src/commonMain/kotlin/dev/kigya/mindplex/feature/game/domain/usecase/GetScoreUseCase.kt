package dev.kigya.mindplex.feature.game.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.outcome.Outcome
import dev.kigya.outcome.unwrap
import kotlinx.coroutines.flow.first

class GetScoreUseCase(
    private val profileDatabaseRepositoryContract: UserProfileDatabaseRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Outcome<*, Int>, None>() {

    override suspend operator fun invoke(params: None): Outcome<*, Int> {
        val token = signInPreferencesRepositoryContract.userId.first()
        return profileDatabaseRepositoryContract.getUserScore(token.orEmpty()).unwrap(
            onSuccess = { Outcome.success(it) },
            onFailure = { Outcome.failure(Unit) },
        )
    }
}
