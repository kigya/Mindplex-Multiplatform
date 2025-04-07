package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel

interface ProfileDatabaseRepositoryContract {
    suspend fun getTopUsersByToken(token: String): Result<UserProfileDomainModel>
    suspend fun saveUser(
        user: UserProfileDomainModel,
        token: String,
    ): Result<UserProfileDomainModel>
}
