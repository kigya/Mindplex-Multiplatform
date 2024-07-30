package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserRemoteProfile
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

internal fun UserRemoteProfile.toDomain(): UserProfileDomainModel = UserProfileDomainModel(
    displayName = name,
    profilePictureUrl = avatarUrl,
)
