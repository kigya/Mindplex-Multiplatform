package dev.kigya.mindplex.feature.leaderboard.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalData
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalRank
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel

internal object UserLocalRankMapper : DomainMapper<UserLocalRank, UserRankDomainModel>() {

    override fun mapToDomainModel(entity: UserLocalRank): UserRankDomainModel = UserRankDomainModel(
        displayName = entity.userLocalData?.name.orEmpty(),
        profilePictureUrl = entity.userLocalData?.avatar.orEmpty(),
        userCountry = entity.userLocalData?.countryCode.orEmpty(),
        score = entity.userLocalData?.score ?: 0,
    )

    override fun mapFromDomainModel(domainModel: UserRankDomainModel): UserLocalRank =
        UserLocalRank(
            id = domainModel.displayName.ifEmpty { domainModel.profilePictureUrl.orEmpty() },
            userLocalData = UserLocalData(
                name = domainModel.displayName,
                avatar = domainModel.profilePictureUrl.orEmpty(),
                countryCode = domainModel.userCountry.orEmpty(),
                score = domainModel.score,
            ),
        )
}
