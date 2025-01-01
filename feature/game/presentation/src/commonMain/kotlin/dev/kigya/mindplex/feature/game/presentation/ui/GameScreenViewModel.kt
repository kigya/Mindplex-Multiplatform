package dev.kigya.mindplex.feature.game.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.game.presentation.contract.GameContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract

class GameScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<GameContract.State, GameContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = GameContract.State(),
),
    GameContract {

    override fun handleEvent(event: GameContract.Event) = withUseCaseScope {
    }
}
