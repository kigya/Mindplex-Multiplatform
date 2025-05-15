package dev.kigya.mindplex.core.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteProfileDto(
    @SerialName("id") val id: String,
    @SerialName("avatarUrl") val avatarUrl: String,
    @SerialName("countryCode") val countryCode: String?,
    @SerialName("displayName") val displayName: String,
    @SerialName("score") val score: Int,
    @SerialName("globalRank") val globalRank: Int,
    @SerialName("localRank") val localRank: Int,
)
