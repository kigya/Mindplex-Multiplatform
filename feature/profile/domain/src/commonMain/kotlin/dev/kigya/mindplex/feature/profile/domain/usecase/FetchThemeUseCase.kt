package dev.kigya.mindplex.feature.profile.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.feature.profile.domain.contract.PreferencesRepositoryContract

class FetchThemeUseCase(
    private val preferencesContract: PreferencesRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Boolean>, None>() {

    override suspend fun invoke(params: None): Either<MindplexDomainError, Boolean> = either {
        val savedTheme = preferencesContract.getTheme()
        val theme = savedTheme ?: false
        theme
    }
}
