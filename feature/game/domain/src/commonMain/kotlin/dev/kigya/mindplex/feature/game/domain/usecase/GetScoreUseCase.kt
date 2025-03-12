package dev.kigya.mindplex.feature.game.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.profile.contract.UserProfileDatabaseRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.flow.first

class GetScoreUseCase(
    private val profileDatabaseRepositoryContract: UserProfileDatabaseRepositoryContract,
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Either<None, Int>, None>() {

    override suspend operator fun invoke(params: None): Either<None, Int> = either {
        val token = signInPreferencesRepositoryContract.userToken.first()
        profileDatabaseRepositoryContract.getUserScore(token.orEmpty()).fold(
            onSuccess = { it },
            onFailure = { raise(None) },
        )
    }
}
