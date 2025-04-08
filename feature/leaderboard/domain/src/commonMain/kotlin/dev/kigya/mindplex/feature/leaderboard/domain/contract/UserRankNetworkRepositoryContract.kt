package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

interface UserRankNetworkRepositoryContract {
    suspend fun getTopUsersByScore(userLimit: Int): Result<List<UserRankDomainModel>>
}
