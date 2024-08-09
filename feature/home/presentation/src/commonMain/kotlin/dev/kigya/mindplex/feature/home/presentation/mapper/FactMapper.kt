package dev.kigya.mindplex.feature.home.presentation.mapper

import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

internal fun List<FactDomainModel>.toUi(): PersistentList<String> =
    map(FactDomainModel::fact).toPersistentList()
