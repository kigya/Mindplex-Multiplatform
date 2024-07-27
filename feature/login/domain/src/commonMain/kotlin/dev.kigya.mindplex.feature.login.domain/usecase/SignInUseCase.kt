package dev.kigya.mindplex.feature.login.domain.usecase

import androidx.annotation.CheckResult
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import dev.kigya.mindplex.core.domain.connectivity.contract.ConnectivityRepositoryContract
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.util.dsl.requireNotNullOrRaise
import dev.kigya.mindplex.feature.login.domain.contract.JwtHandlerContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInNetworkRepositoryContract
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

class SignInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
    private val signInNetworkRepositoryContract: SignInNetworkRepositoryContract,
    private val jwtHandlerContract: JwtHandlerContract,
    private val connectivityRepositoryContract: ConnectivityRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Unit>, GoogleUserSignInDomainModel?>() {

    @CheckResult
    override suspend operator fun invoke(
        params: GoogleUserSignInDomainModel?,
    ): Either<MindplexDomainError, Unit> = either {
        requireNotNullOrRaise(params) {
            ensure(connectivityRepositoryContract.isConnected().not()) { MindplexDomainError.OTHER }
            raise(MindplexDomainError.NETWORK)
        }.run {
            val userIdResult = jwtHandlerContract.decodeSubject(tokenId)
            userIdResult.fold(
                onSuccess = { userId ->
                    val user = copy(tokenId = userId)
                    signInPreferencesRepositoryContract.signIn(user.tokenId)
                    signInNetworkRepositoryContract.signIn(user)
                    Unit.right()
                },
                onFailure = { raise(MindplexDomainError.OTHER) },
            )
        }
    }
}
