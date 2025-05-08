package dev.kigya.mindplex.feature.leaderboard.presentation.mapper

import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.leaderboard.domain.model.UserRankDomainModel
import dev.kigya.mindplex.feature.leaderboard.presentation.contract.LeaderboardContract

internal object UserCardMapper : DomainMapper<UserRankDomainModel, LeaderboardContract.State.UserCardData>() {

    override fun mapToDomainModel(
        entity: UserRankDomainModel,
    ): LeaderboardContract.State.UserCardData = LeaderboardContract.State.UserCardData(
        userName = entity.displayName,
        userScore = entity.score.toString(),
        avatarUrl = entity.profilePictureUrl,
        userRank = String.empty,
        countryCode = entity.userCountry,
    )

    override fun mapFromDomainModel(
        domainModel: LeaderboardContract.State.UserCardData,
    ): UserRankDomainModel = UserRankDomainModel(
        displayName = domainModel.userName,
        profilePictureUrl = domainModel.avatarUrl,
        userCountry = domainModel.countryCode.orEmpty(),
        score = domainModel.userScore.toIntOrNull() ?: 0,
        id = domainModel.id,
    )
}
