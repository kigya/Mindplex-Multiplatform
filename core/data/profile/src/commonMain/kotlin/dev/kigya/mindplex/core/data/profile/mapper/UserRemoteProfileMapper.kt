package dev.kigya.mindplex.core.data.profile.mapper

import dev.kigya.mindplex.core.data.profile.model.UserRemoteProfileDto
import dev.kigya.mindplex.core.domain.profile.model.UserProfileDomainModel
import dev.kigya.mindplex.core.util.mapper.DomainMapper

internal object UserRemoteProfileMapper : DomainMapper<UserRemoteProfileDto, UserProfileDomainModel>() {

    override fun mapToDomainModel(entity: UserRemoteProfileDto): UserProfileDomainModel =
        UserProfileDomainModel(
            displayName = entity.displayName,
            profilePictureUrl = entity.avatarUrl,
            score = entity.score,
            userCountry = entity.countryCode,
            globalRank = entity.globalRank,
            localRank = entity.localRank,
        )

    override fun mapFromDomainModel(domainModel: UserProfileDomainModel): UserRemoteProfileDto =
        UserRemoteProfileDto(
            displayName = domainModel.displayName,
            avatarUrl = domainModel.profilePictureUrl.orEmpty(),
            score = domainModel.score,
            countryCode = domainModel.userCountry.orEmpty(),
            globalRank = domainModel.globalRank ?: 0,
            localRank = domainModel.localRank ?: 0,
        )
}
