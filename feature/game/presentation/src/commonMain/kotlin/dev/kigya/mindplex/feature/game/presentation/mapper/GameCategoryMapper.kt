package dev.kigya.mindplex.feature.game.presentation.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.domain.model.CategoryDomainModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object GameCategoryMapper :
    DomainMapper<ScreenRoute.Game.CategoryPresentationModel, CategoryDomainModel>() {

    override fun mapToDomainModel(
        entity: ScreenRoute.Game.CategoryPresentationModel,
    ): CategoryDomainModel = outcomeCatching {
        CategoryDomainModel.valueOf(entity.name)
    }.getOrDefault(CategoryDomainModel.GENERAL_KNOWLEDGE)

    override fun mapFromDomainModel(
        domainModel: CategoryDomainModel,
    ): ScreenRoute.Game.CategoryPresentationModel = outcomeCatching {
        ScreenRoute.Game.CategoryPresentationModel.valueOf(domainModel.name)
    }.getOrDefault(ScreenRoute.Game.CategoryPresentationModel.GENERAL_KNOWLEDGE)
}
