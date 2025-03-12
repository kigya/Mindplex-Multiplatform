package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.remote.TypeDto
import dev.kigya.mindplex.feature.game.domain.model.TypeDomainModel

internal object TypeDtoMapper : DomainMapper<TypeDto, TypeDomainModel>() {

    override fun mapToDomainModel(entity: TypeDto): TypeDomainModel = when (entity) {
        TypeDto.BOOLEAN -> TypeDomainModel.BOOLEAN
        TypeDto.MULTIPLE -> TypeDomainModel.MULTIPLE
    }

    override fun mapFromDomainModel(domainModel: TypeDomainModel): TypeDto = when (domainModel) {
        TypeDomainModel.BOOLEAN -> TypeDto.BOOLEAN
        TypeDomainModel.MULTIPLE -> TypeDto.MULTIPLE
        TypeDomainModel.RANDOM -> TypeDto.entries.random()
    }
}
