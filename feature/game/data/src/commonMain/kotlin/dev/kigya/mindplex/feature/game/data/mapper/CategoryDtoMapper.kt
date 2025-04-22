package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.remote.CategoryDto
import dev.kigya.mindplex.feature.game.domain.model.CategoryDomainModel
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object CategoryDtoMapper : DomainMapper<CategoryDto, CategoryDomainModel>() {

    override fun mapToDomainModel(entity: CategoryDto): CategoryDomainModel = outcomeCatching {
        CategoryDomainModel.valueOf(entity.name)
    }.getOrDefault(CategoryDomainModel.GENERAL_KNOWLEDGE)

    override fun mapFromDomainModel(domainModel: CategoryDomainModel): CategoryDto =
        outcomeCatching {
            CategoryDto.valueOf(domainModel.name)
        }.getOrDefault(CategoryDto.GENERAL_KNOWLEDGE)
}
