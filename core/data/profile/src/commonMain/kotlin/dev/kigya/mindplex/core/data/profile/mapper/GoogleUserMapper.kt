package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.GoogleRemoteUser
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

internal fun GoogleRemoteUser.toDomain(): UserProfileDomainModel = UserProfileDomainModel(
    displayName = name,
    profilePictureUrl = avatarUrl,
)
