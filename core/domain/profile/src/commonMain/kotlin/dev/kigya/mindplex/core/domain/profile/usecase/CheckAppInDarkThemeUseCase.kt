package dev.kigya.mindplex.core.domain.profile.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.None
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.domain.profile.contract.ThemePreferencesRepositoryContract
import dev.kigya.outcome.Outcome
import kotlinx.coroutines.flow.first

class CheckAppInDarkThemeUseCase(
    private val preferencesContract: ThemePreferencesRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, Boolean>, None>() {

    override suspend fun invoke(params: None): Outcome<MindplexDomainError, Boolean> =
        Outcome.success(preferencesContract.isInDarkTheme.first())
}
