package dev.kigya.mindplex.feature.profile.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.profile.data.model.UserRemoteRankDto
import dev.kigya.mindplex.feature.profile.domain.model.UserRankDomainModel

internal object UserRemoteRankMapper : DomainMapper<UserRemoteRankDto, UserRankDomainModel>() {

    override fun mapToDomainModel(entity: UserRemoteRankDto): UserRankDomainModel =
        UserRankDomainModel(
            displayName = entity.name,
            profilePictureUrl = entity.avatarUrl,
            score = entity.score,
            userCountry = entity.countryCode,
        )

    override fun mapFromDomainModel(domainModel: UserRankDomainModel): UserRemoteRankDto =
        UserRemoteRankDto(
            name = domainModel.displayName,
            avatarUrl = domainModel.profilePictureUrl.orEmpty(),
            score = domainModel.score,
            countryCode = domainModel.userCountry.orEmpty(),
        )
}
