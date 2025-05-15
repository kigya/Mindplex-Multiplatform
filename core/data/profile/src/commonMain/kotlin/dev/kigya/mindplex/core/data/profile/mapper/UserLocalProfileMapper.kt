package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserLocalData
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.core.util.mapper.DomainMapper

internal object UserLocalProfileMapper : DomainMapper<UserLocalProfile, UserProfileDomainModel>() {

    override fun mapToDomainModel(entity: UserLocalProfile): UserProfileDomainModel =
        UserProfileDomainModel(
            displayName = entity.userLocalData?.name.orEmpty(),
            profilePictureUrl = entity.userLocalData?.avatar.orEmpty(),
            userCountry = entity.userLocalData?.countryCode.orEmpty(),
            score = entity.userLocalData?.score ?: 0,
            globalRank = entity.userLocalData?.globalRank,
            localRank = entity.userLocalData?.localRank,
            id = entity.id,
        )

    override fun mapFromDomainModel(domainModel: UserProfileDomainModel): UserLocalProfile =
        UserLocalProfile(
            id = domainModel.displayName.ifEmpty { domainModel.profilePictureUrl.orEmpty() },
            userLocalData = UserLocalData(
                name = domainModel.displayName,
                avatar = domainModel.profilePictureUrl.orEmpty(),
                countryCode = domainModel.userCountry.orEmpty(),
                score = domainModel.score,
                globalRank = domainModel.globalRank ?: 0,
                localRank = domainModel.localRank ?: 0,
            ),
        )
}
