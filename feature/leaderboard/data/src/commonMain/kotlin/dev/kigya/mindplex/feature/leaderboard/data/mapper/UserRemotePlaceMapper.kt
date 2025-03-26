package dev.kigya.mindplex.feature.leaderboard.data.mapper

import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemotePlaceDto
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel

internal fun UserRemotePlaceDto.toDomain(): UserPlaceDomainModel = UserPlaceDomainModel(
    displayName = name,
    profilePictureUrl = avatarUrl,
    score = score,
)
