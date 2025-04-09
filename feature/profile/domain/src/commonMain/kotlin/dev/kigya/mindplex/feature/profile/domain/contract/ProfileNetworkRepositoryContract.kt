package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.ProfileDomainModel

interface ProfileNetworkRepositoryContract {
    suspend fun getTopUsersByToken(token: String): Result<ProfileDomainModel>
}
