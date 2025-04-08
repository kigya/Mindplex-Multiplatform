package dev.kigya.mindplex.feature.profile.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.profile.data.model.UserRemoteProfileDto
import dev.kigya.mindplex.feature.profile.domain.model.UserProfileDomainModel

internal object UserRemoteProfileMapper : DomainMapper<UserRemoteProfileDto, UserProfileDomainModel>() {

    override fun mapToDomainModel(entity: UserRemoteProfileDto): UserProfileDomainModel =
        UserProfileDomainModel(
            displayName = entity.name,
            profilePictureUrl = entity.avatarUrl,
            score = entity.score,
            userCountry = entity.countryCode,
            globalRank = entity.globalRank,
            localRank = entity.localRank,
        )

    override fun mapFromDomainModel(domainModel: UserProfileDomainModel): UserRemoteProfileDto =
        UserRemoteProfileDto(
            name = domainModel.displayName,
            avatarUrl = domainModel.profilePictureUrl.orEmpty(),
            score = domainModel.score,
            countryCode = domainModel.userCountry.orEmpty(),
            globalRank = domainModel.globalRank ?: 0,
            localRank = domainModel.localRank ?: 0,
        )
}
