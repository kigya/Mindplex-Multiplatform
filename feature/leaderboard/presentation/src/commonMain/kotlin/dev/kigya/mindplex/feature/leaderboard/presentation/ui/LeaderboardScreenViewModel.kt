package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.navigation.navigator.navigator.MindplexNavigatorContract

class LeaderboardScreenViewModel(
    navigatorContract: MindplexNavigatorContract,
) : BaseViewModel<LeaderboardContract.State, LeaderboardContract.Effect>(
    navigatorContract = navigatorContract,
    initialState = LeaderboardContract.State(),
),
    LeaderboardContract {
    @Suppress("NotImplementedDeclaration")
    override fun handleEvent(event: LeaderboardContract.Event) = TODO()
}
