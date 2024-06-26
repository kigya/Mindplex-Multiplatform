package dev.kigya.mindplex.feature.login.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.flow.Flow

class GetIsUserSignedInUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseUseCase<Flow<Boolean>, None>() {
    override operator fun invoke(params: None) = signInPreferencesRepositoryContract.isSignedIn
}
