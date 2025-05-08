package dev.kigya.mindplex.core.domain.profile.contract

import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.outcome.Outcome

interface UserProfileNetworkRepositoryContract {
    suspend fun getUserProfile(jwtToken: String): Outcome<*, UserProfileDomainModel>
    suspend fun updateUserScore(
        jwtToken: String,
        score: Int,
    )
}
