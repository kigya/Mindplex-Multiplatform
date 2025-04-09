package dev.kigya.mindplex.feature.profile.domain.model

data class ProfileDomainModel(
    val displayName: String,
    val profilePictureUrl: String?,
    val userCountry: String?,
    val score: Int,
    val globalRank: Int?,
    val localRank: Int?,
)
