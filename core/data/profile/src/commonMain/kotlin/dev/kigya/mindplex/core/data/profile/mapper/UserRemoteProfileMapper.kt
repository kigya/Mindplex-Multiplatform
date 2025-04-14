package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserRemoteProfileDto
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

fun UserRemoteProfileDto.toDomain(): UserProfileDomainModel = UserProfileDomainModel(
    displayName = name,
    profilePictureUrl = avatarUrl,
    score = score,
    userCountry = countryCode,
    globalRank = globalRank,
    localRank = localRank,
)
