package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PublicIPResponse(
    @SerialName("ip")
    val ip: String,
)
