package dev.kigya.mindplex.feature.leaderboard.presentation.ui

import dev.kigya.mindplex.core.presentation.feature.BaseViewModel
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract.Effect
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract.State

class LeaderboardScreenViewModel : BaseViewModel<State, Effect>(State()), LeaderboardContract {
    @Suppress("NotImplementedDeclaration")
    override fun handleEvent(event: LeaderboardContract.Event) = TODO()
}
