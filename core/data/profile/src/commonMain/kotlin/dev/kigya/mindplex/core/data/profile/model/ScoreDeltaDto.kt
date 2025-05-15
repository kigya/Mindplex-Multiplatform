package dev.kigya.mindplex.core.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreDeltaDto(
    @SerialName("delta") val newScore: Int,
)
