package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

interface UserProfileNetworkRepositoryContract {
    suspend fun getUserProfile(token: String): Result<UserProfileDomainModel>
}
