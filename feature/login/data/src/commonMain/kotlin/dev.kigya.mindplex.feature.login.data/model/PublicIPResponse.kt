package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PublicIPResponse(
    val ip: String,
)
