package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel

interface UserPlaceDatabaseRepositoryContract {
    suspend fun getTopUsersByScore(): Result<List<UserPlaceDomainModel>>
    suspend fun saveUsers(users: List<UserPlaceDomainModel>): Result<List<UserPlaceDomainModel>>
}
