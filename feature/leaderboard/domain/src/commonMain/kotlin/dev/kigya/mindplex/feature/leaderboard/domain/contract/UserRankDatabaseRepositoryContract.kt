package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.outcome.Outcome

interface UserRankDatabaseRepositoryContract {
    suspend fun getTopUsersByScore(limit: Int): Outcome<*, List<UserRankDomainModel>>
    suspend fun saveUsers(users: List<UserRankDomainModel>): Outcome<*, List<UserRankDomainModel>>
}
