package dev.kigya.mindplex.feature.profile.domain.contract

import dev.kigya.mindplex.feature.profile.domain.model.UserRankDomainModel

interface UserRankNetworkRepositoryContract {
    suspend fun getTopUsersByScore(userLimit: Int): Result<List<UserRankDomainModel>>
}
