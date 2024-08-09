package dev.kigya.mindpex.feature.home.data.mapper

import dev.kigya.mindpex.feature.home.data.model.FactLocal
import dev.kigya.mindpex.feature.home.data.model.FactRemoteDto
import dev.kigya.mindplex.feature.home.domain.model.FactDomainModel
import kotlin.jvm.JvmName

@JvmName("toDomainFromFactRemoteDto")
internal fun List<FactRemoteDto>.toDomain(): List<FactDomainModel> =
    filterNot { isNullOrEmpty() }.map { FactDomainModel(fact = it.fact) }

internal fun List<FactDomainModel>.toLocalEntity(): List<FactLocal> =
    map { FactLocal(fact = it.fact) }

@JvmName("toDomainFromFactLocal")
internal fun List<FactLocal?>.toDomain(): List<FactDomainModel> = filterNotNull().map {
    FactDomainModel(
        fact = it.fact,
        timestamp = it.timestamp,
    )
}
