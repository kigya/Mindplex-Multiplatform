package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserLocalData
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

internal fun UserLocalProfile.toDomain(): UserProfileDomainModel = UserProfileDomainModel(
    displayName = userLocalData?.name.orEmpty(),
    profilePictureUrl = userLocalData?.name.orEmpty(),
    score = userLocalData?.score ?: 0,
)

internal fun UserProfileDomainModel.toDatabaseEntry(token: String): UserLocalProfile =
    UserLocalProfile(
        id = token,
        userLocalData = UserLocalData(
            name = displayName,
            avatar = profilePictureUrl.orEmpty(),
            score = score,
        ),
    )
