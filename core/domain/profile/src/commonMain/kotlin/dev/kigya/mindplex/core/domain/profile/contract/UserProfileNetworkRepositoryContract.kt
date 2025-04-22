package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.outcome.Outcome

interface UserProfileNetworkRepositoryContract {
    suspend fun getUserProfile(userId: String): Outcome<*, UserProfileDomainModel>
    suspend fun updateUserScore(
        userId: String,
        score: Int,
    )
}
