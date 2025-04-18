package dev.kigya.mindplex.core.domain.profile.model

data class UserProfileDomainModel(
    val displayName: String,
    val profilePictureUrl: String?,
    val userCountry: String?,
    val score: Int,
    val globalRank: Int? = null,
    val localRank: Int? = null,
)
