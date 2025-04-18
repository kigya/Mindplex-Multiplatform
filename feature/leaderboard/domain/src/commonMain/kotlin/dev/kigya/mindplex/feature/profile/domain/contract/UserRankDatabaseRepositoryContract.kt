package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.UserRankDomainModel

interface UserRankDatabaseRepositoryContract {
    suspend fun getTopUsersByScore(limit: Int): Result<List<UserRankDomainModel>>
    suspend fun saveUsers(users: List<UserRankDomainModel>): Result<List<UserRankDomainModel>>
}
