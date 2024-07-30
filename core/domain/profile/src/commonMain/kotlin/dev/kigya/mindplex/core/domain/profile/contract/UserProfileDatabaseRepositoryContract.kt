package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

interface UserProfileDatabaseRepositoryContract {
    suspend fun getUserProfile(token: String): Result<UserProfileDomainModel>
    suspend fun saveUserProfile(token: String, profile: UserProfileDomainModel)
}
