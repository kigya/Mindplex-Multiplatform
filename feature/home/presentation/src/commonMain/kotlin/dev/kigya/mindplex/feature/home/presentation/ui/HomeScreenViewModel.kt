package dev.kigya.mindplex.feature.home.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract

class HomeScreenViewModel : BaseViewModel<HomeContract.State, HomeContract.Effect>(HomeContract.State()), HomeContract {

    override fun handleEvent(event: HomeContract.Event) = withUseCaseScope {
        when (event) {
            is HomeContract.Event.OnFirstLaunch -> {}
        }
    }
}
