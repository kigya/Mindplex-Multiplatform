package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.local.TypeLocal
import dev.kigya.mindplex.feature.game.domain.model.TypeDomainModel

internal object TypeLocalMapper : DomainMapper<TypeLocal, TypeDomainModel>() {

    override fun mapToDomainModel(entity: TypeLocal): TypeDomainModel = when (entity) {
        TypeLocal.BOOLEAN -> TypeDomainModel.BOOLEAN
        TypeLocal.MULTIPLE -> TypeDomainModel.MULTIPLE
    }

    override fun mapFromDomainModel(domainModel: TypeDomainModel): TypeLocal = when (domainModel) {
        TypeDomainModel.BOOLEAN -> TypeLocal.BOOLEAN
        TypeDomainModel.MULTIPLE -> TypeLocal.MULTIPLE
        TypeDomainModel.RANDOM -> TypeLocal.entries.random()
    }
}
