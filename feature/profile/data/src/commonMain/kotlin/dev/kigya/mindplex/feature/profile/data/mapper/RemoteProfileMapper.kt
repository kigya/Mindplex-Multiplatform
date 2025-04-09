package dev.kigya.mindplex.feature.profile.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.profile.data.model.RemoteProfileDto
import dev.kigya.mindplex.feature.profile.domain.model.ProfileDomainModel

internal object RemoteProfileMapper : DomainMapper<RemoteProfileDto, ProfileDomainModel>() {

    override fun mapToDomainModel(entity: RemoteProfileDto): ProfileDomainModel =
        ProfileDomainModel(
            displayName = entity.name,
            profilePictureUrl = entity.avatarUrl,
            score = entity.score,
            userCountry = entity.countryCode,
            globalRank = entity.globalRank,
            localRank = entity.localRank,
        )

    override fun mapFromDomainModel(domainModel: ProfileDomainModel): RemoteProfileDto =
        RemoteProfileDto(
            name = domainModel.displayName,
            avatarUrl = domainModel.profilePictureUrl.orEmpty(),
            score = domainModel.score,
            countryCode = domainModel.userCountry.orEmpty(),
            globalRank = domainModel.globalRank ?: 0,
            localRank = domainModel.localRank ?: 0,
        )
}
