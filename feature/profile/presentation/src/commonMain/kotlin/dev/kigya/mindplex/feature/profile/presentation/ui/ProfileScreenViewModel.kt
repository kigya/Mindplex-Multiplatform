package dev.kigya.mindplex.feature.profile.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract.Effect
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract.State

class ProfileScreenViewModel : BaseViewModel<State, Effect>(State()), ProfileContract {
    @Suppress("NotImplementedDeclaration")
    override fun handleEvent(event: ProfileContract.Event) = TODO()
}
