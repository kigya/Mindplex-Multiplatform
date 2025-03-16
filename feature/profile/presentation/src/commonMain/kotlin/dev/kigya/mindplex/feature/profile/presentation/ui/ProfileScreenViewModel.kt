package dev.kigya.mindplex.feature.profile.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.profile.presentation.contract.ProfileContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract

class ProfileScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<ProfileContract.State, ProfileContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = ProfileContract.State(),
),
    ProfileContract {
    @Suppress("NotImplementedDeclaration")
    override fun handleEvent(event: ProfileContract.Event) = TODO()
}
