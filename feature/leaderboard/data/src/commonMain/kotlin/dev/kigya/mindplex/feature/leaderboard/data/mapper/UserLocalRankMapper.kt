package dev.kigya.mindplex.feature.leaderboard.data.mapper

import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalData
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalRank
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

internal fun UserLocalRank.toDomain(): UserRankDomainModel = UserRankDomainModel(
    displayName = userLocalData?.name.orEmpty(),
    profilePictureUrl = userLocalData?.avatar.orEmpty(),
    userCountry = userLocalData?.countryCode.orEmpty(),
    score = userLocalData?.score ?: 0,
)

internal fun UserRankDomainModel.toDatabaseEntry(token: String): UserLocalRank = UserLocalRank(
    id = token,
    userLocalData = UserLocalData(
        name = displayName,
        avatar = profilePictureUrl.orEmpty(),
        countryCode = userCountry.orEmpty(),
        score = score,
    ),
)
