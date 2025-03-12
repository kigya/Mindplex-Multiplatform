package dev.kigya.mindplex.feature.game.presentation.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.domain.model.DifficultyDomainModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute

internal object GameDifficultyMapper :
    DomainMapper<ScreenRoute.Game.DifficultyPresentationModel, DifficultyDomainModel>() {

    override fun mapToDomainModel(
        entity: ScreenRoute.Game.DifficultyPresentationModel,
    ): DifficultyDomainModel = runCatching {
        DifficultyDomainModel.valueOf(entity.name)
    }.getOrDefault(DifficultyDomainModel.EASY)

    override fun mapFromDomainModel(
        domainModel: DifficultyDomainModel,
    ): ScreenRoute.Game.DifficultyPresentationModel = runCatching {
        ScreenRoute.Game.DifficultyPresentationModel.valueOf(domainModel.name)
    }.getOrDefault(ScreenRoute.Game.DifficultyPresentationModel.EASY)
}
