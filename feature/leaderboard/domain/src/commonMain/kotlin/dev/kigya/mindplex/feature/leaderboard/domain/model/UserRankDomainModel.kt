package dev.kigya.mindplex.feature.leaderboard.domain.model

import dev.kigya.mindplex.core.util.extension.empty

data class UserRankDomainModel(
    val displayName: String = String.empty,
    val profilePictureUrl: String? = null,
    val userCountry: String? = null,
    val score: Int = 0,
)
