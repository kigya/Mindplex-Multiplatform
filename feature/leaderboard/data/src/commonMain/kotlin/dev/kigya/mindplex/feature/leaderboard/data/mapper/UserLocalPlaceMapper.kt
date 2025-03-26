package dev.kigya.mindplex.feature.leaderboard.data.mapper

import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalData
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalPlace
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserPlaceDomainModel

internal fun UserLocalPlace.toDomain(): UserPlaceDomainModel = UserPlaceDomainModel(
    displayName = userLocalData?.name.orEmpty(),
    profilePictureUrl = userLocalData?.name.orEmpty(),
    score = userLocalData?.score ?: 0,
)

internal fun UserPlaceDomainModel.toDatabaseEntry(token: String): UserLocalPlace = UserLocalPlace(
    id = token,
    userLocalData = UserLocalData(
        name = displayName,
        avatar = profilePictureUrl.orEmpty(),
        score = score,
    ),
)
