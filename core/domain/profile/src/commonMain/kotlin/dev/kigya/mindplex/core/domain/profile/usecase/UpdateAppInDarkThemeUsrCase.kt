package dev.kigya.mindplex.core.domain.profile.usecase

import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.domain.profile.contract.ThemePreferencesRepositoryContract
import dev.kigya.outcome.Outcome

class UpdateAppInDarkThemeUsrCase(
    private val preferencesContract: ThemePreferencesRepositoryContract,
) : BaseSuspendUseCase<Outcome<MindplexDomainError, Boolean>, Boolean>() {

    override suspend fun invoke(params: Boolean): Outcome<MindplexDomainError, Boolean> {
        preferencesContract.saveTheme(params)
        return Outcome.success(params)
    }
}
