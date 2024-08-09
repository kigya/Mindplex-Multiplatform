package dev.kigya.mindpex.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactRemoteDto(
    @SerialName("fact") val fact: String,
)
