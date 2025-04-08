package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

interface UserRankDatabaseRepositoryContract {
    suspend fun getTopUsersByScore(limit: Int): Result<List<UserRankDomainModel>>
    suspend fun saveUsers(users: List<UserRankDomainModel>): Result<List<UserRankDomainModel>>
}
