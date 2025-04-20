package dev.kigya.mindplex.feature.login.domain.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract

class SignOutUseCase(
    private val signInPreferencesRepositoryContract: SignInPreferencesRepositoryContract,
) : BaseSuspendUseCase<Unit, None>() {

    override suspend fun invoke(params: None) = signInPreferencesRepositoryContract.signOut()
}
