package dev.kigya.mindplex.feature.leaderboard.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserRemoteRankDto(
    @SerialName("id") val id: String,
    @SerialName("avatarUrl") val avatarUrl: String,
    @SerialName("countryCode") val countryCode: String,
    @SerialName("displayName") val name: String,
    @SerialName("score") val score: Int,
)
