package dev.kigya.mindplex.feature.leaderboard.domain.contract

import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel

interface UserPlaceNetworkRepositoryContract {
    suspend fun getTopUsersByScore(): Result<List<UserPlaceDomainModel>>
}
