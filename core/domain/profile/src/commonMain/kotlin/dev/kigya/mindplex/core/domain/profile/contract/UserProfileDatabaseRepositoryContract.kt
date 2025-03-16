package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

interface UserProfileDatabaseRepositoryContract {
    suspend fun getUserProfile(token: String): Result<UserProfileDomainModel>

    suspend fun getUserScore(token: String): Result<Int>

    suspend fun saveUserScore(
        token: String,
        score: Int,
    )

    suspend fun saveUserProfile(
        token: String,
        profile: UserProfileDomainModel,
    )
}
