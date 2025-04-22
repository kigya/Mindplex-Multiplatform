package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.outcome.Outcome

interface UserProfileDatabaseRepositoryContract {
    suspend fun getUserProfile(userId: String): Outcome<*, UserProfileDomainModel>

    suspend fun getUserScore(userId: String): Outcome<*, Int>

    suspend fun saveUserScore(
        userId: String,
        score: Int,
    )

    suspend fun saveUserProfile(
        userId: String,
        profile: UserProfileDomainModel,
    )
}
