package dev.kigya.mindplex.feature.leaderboard.domain.model

data class UserRankDomainModel(
    val displayName: String,
    val profilePictureUrl: String?,
    val userCountry: String?,
    val score: Int,
)
