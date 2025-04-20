package dev.kigya.mindplex.feature.game.presentation.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.domain.model.TypeDomainModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object GameTypeMapper : DomainMapper<TypePresentationModel, TypeDomainModel>() {

    override fun mapToDomainModel(entity: TypePresentationModel): TypeDomainModel =
        outcomeCatching {
            TypeDomainModel.valueOf(entity.name)
        }.getOrDefault(TypeDomainModel.RANDOM)

    override fun mapFromDomainModel(domainModel: TypeDomainModel): TypePresentationModel =
        outcomeCatching {
            TypePresentationModel.valueOf(domainModel.name)
        }.getOrDefault(TypePresentationModel.RANDOM)
}
