package dev.kigya.mindplex.core.domain.profile.usecase

import arrow.core.Either
import arrow.core.raise.either
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.domain.profile.contract.ThemePreferencesRepositoryContract
import kotlinx.coroutines.flow.first

class IsSystemDarkThemeUseCase(
    private val preferencesContract: ThemePreferencesRepositoryContract,
) : BaseSuspendUseCase<Either<MindplexDomainError, Boolean>, None>() {

    override suspend fun invoke(params: None): Either<MindplexDomainError, Boolean> = either {
        preferencesContract.isInDarkTheme.first()
    }
}
