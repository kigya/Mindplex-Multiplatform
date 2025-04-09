package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.ProfileDomainModel

interface ProfileDatabaseRepositoryContract {
    suspend fun getTopUsersByToken(token: String): Result<ProfileDomainModel>
    suspend fun saveUser(
        user: ProfileDomainModel,
        token: String,
    ): Result<ProfileDomainModel>
}
