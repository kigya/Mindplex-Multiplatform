package dev.kigya.mindplex.core.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreUpdateResponseDto(
    @SerialName("newScore") val newScore: Int,
)
