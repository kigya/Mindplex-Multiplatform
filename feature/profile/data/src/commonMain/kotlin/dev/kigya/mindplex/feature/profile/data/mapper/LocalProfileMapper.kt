package dev.kigya.mindplex.feature.profile.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.profile.data.model.LocalProfile
import dev.kigya.mindplex.feature.profile.data.model.ProfileLocalData
import dev.kigya.mindplex.feature.profile.domain.model.ProfileDomainModel

internal object LocalProfileMapper : DomainMapper<LocalProfile, ProfileDomainModel>() {

    override fun mapToDomainModel(entity: LocalProfile): ProfileDomainModel = ProfileDomainModel(
        displayName = entity.profileLocalData?.name.orEmpty(),
        profilePictureUrl = entity.profileLocalData?.avatar.orEmpty(),
        userCountry = entity.profileLocalData?.countryCode.orEmpty(),
        score = entity.profileLocalData?.score ?: 0,
        globalRank = entity.profileLocalData?.globalRank ?: 0,
        localRank = entity.profileLocalData?.localRank ?: 0,
    )

    override fun mapFromDomainModel(domainModel: ProfileDomainModel): LocalProfile = LocalProfile(
        id = domainModel.displayName,
        profileLocalData = ProfileLocalData(
            name = domainModel.displayName,
            avatar = domainModel.profilePictureUrl.orEmpty(),
            countryCode = domainModel.userCountry.orEmpty(),
            score = domainModel.score,
            globalRank = domainModel.globalRank ?: 0,
            localRank = domainModel.localRank ?: 0,
        ),
    )
}
