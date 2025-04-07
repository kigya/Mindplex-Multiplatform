package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel

interface ProfileNetworkRepositoryContract {
    suspend fun getTopUsersByToken(token: String): Result<UserProfileDomainModel>
}
