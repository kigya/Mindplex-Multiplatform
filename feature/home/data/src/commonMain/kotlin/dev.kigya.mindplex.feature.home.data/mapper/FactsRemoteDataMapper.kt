package dev.kigya.mindplex.feature.home.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel

internal object FactsRemoteDataMapper : DomainMapper<List<FactRemoteDto>, List<FactDomainModel>>() {

    override fun mapToDomainModel(entity: List<FactRemoteDto>): List<FactDomainModel> =
        entity.filterNot { entity.isEmpty() }.map { FactDomainModel(fact = it.fact) }
}
