package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome

interface UserRankNetworkRepositoryContract {
    suspend fun getTopUsersByScore(userLimit: Int): Outcome<*, List<UserRankDomainModel>>
}
