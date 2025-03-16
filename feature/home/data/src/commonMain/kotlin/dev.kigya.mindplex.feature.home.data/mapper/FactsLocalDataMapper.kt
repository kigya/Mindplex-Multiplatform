package dev.kigya.mindplex.feature.home.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.home.data.model.FactLocal
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel

internal object FactsLocalDataMapper : DomainMapper<List<FactLocal?>, List<FactDomainModel>>() {

    override fun mapFromDomainModel(domainModel: List<FactDomainModel>): List<FactLocal> =
        domainModel.map { FactLocal(fact = it.fact) }

    override fun mapToDomainModel(entity: List<FactLocal?>): List<FactDomainModel> =
        entity.filterNotNull().map {
            FactDomainModel(
                fact = it.fact,
                timestamp = it.timestamp,
            )
        }
}
