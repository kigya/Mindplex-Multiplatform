package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("token") val token: String,
    @SerialName("displayName") val displayName: String,
    @SerialName("avatarUrl") val avatarUrl: String? = null,
)
