package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GeoLocationResponse(
    val country: String? = null,
)
