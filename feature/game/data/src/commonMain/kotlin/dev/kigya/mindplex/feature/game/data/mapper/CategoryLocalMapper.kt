package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.local.CategoryLocal
import dev.kigya.mindplex.feature.game.domain.model.CategoryDomainModel
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object CategoryLocalMapper : DomainMapper<CategoryLocal, CategoryDomainModel>() {

    override fun mapToDomainModel(entity: CategoryLocal): CategoryDomainModel = outcomeCatching {
        CategoryDomainModel.valueOf(entity.name)
    }.getOrDefault(CategoryDomainModel.GENERAL_KNOWLEDGE)

    override fun mapFromDomainModel(domainModel: CategoryDomainModel): CategoryLocal =
        outcomeCatching {
            CategoryLocal.valueOf(domainModel.name)
        }.getOrDefault(CategoryLocal.GENERAL_KNOWLEDGE)
}
