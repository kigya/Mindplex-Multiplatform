package dev.kigya.mindplex.core.domain.profile.model

import dev.kigya.mindplex.core.util.extension.empty

data class UserProfileDomainModel(
    val displayName: String = String.empty,
    val profilePictureUrl: String? = null,
    val score: Int = 0,
)
