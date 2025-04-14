package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserLocalData
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel

fun UserLocalProfile.toDomain(): UserProfileDomainModel = UserProfileDomainModel(
    displayName = userLocalData?.name.orEmpty(),
    profilePictureUrl = userLocalData?.avatar.orEmpty(),
    userCountry = userLocalData?.countryCode.orEmpty(),
    score = userLocalData?.score ?: 0,
    globalRank = userLocalData?.globalRank,
    localRank = userLocalData?.localRank,
)

fun UserProfileDomainModel.toDatabaseEntry(token: String): UserLocalProfile = UserLocalProfile(
    id = token,
    userLocalData = UserLocalData(
        name = displayName,
        avatar = profilePictureUrl.orEmpty(),
        countryCode = userCountry.orEmpty(),
        score = score,
        globalRank = globalRank ?: 0,
        localRank = localRank ?: 0,
    ),
)
