package dev.kigya.mindplex.feature.home.presentation.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

object FactsPresentationMapper : DomainMapper<PersistentList<String>, List<FactDomainModel>>() {

    override fun mapFromDomainModel(domainModel: List<FactDomainModel>): PersistentList<String> =
        domainModel.map(FactDomainModel::fact).toPersistentList()
}
