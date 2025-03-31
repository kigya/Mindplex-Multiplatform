package dev.kigya.mindplex.feature.login.domain.model

import dev.kigya.mindplex.core.util.extension.empty

data class GoogleUserSignInDomainModel(
    val tokenId: String,
    val displayName: String = String.empty,
    val profilePictureUrl: String? = null,
    val countryCode: String? = null,
)
