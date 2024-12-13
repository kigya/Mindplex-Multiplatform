package dev.kigya.mindplex.feature.game.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.Effect
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.Event
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract.State

class GameScreenViewModel : BaseViewModel<State, Effect>(State()), GameContract {

    override fun handleEvent(event: Event) = withUseCaseScope {
    }
}
