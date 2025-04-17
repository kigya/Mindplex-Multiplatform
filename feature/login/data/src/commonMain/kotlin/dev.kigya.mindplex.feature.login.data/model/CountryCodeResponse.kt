package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CountryCodeResponse(
    val countryCode: String? = null,
)
