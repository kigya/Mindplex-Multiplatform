package dev.kigya.mindplex.feature.login.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val token: String,
    val displayName: String,
    val avatarUrl: String? = null,
)
