package dev.kigya.mindplex.feature.profile.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.profile.domain.contract.PreferencesRepositoryContract

class UpdateThemeUseCase(
    private val preferencesContract: PreferencesRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Boolean>, Boolean>() {

    override suspend fun invoke(params: Boolean): Either<MindplexDomainError, Boolean> = either {
        preferencesContract.saveTheme(params)
        params
    }
}
