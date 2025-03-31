package dev.kigya.mindplex.feature.leaderboard.data.mapper

import dev.kigya.mindplex.feature.leaderboard.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

internal fun UserRemoteRankDto.toDomain(): UserRankDomainModel = UserRankDomainModel(
    displayName = name,
    profilePictureUrl = avatarUrl,
    score = score,
    userCountry = countryCode,
)
