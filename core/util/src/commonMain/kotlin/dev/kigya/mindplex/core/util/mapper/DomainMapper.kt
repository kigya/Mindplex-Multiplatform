@file:Suppress("NotImplementedDeclaration")

package dev.kigya.mindplex.core.util.mapper

import androidx.annotation.EmptySuper
import kotlin.jvm.JvmName

abstract class DomainMapper<LayerEntity, DomainModel> {

    @EmptySuper
    open fun mapToDomainModel(entity: LayerEntity): DomainModel =
        throw NotImplementedError("mapToDomainModel is not implemented.")

    @EmptySuper
    open fun mapFromDomainModel(domainModel: DomainModel): LayerEntity =
        throw NotImplementedError("mapFromDomainModel is not implemented.")

    @JvmName("domainMappedBy")
    infix fun <LayerEntity, DomainModel> DomainModel.mappedBy(
        mapper: DomainMapper<LayerEntity, DomainModel>,
    ): LayerEntity = mapper.mapFromDomainModel(this)

    @JvmName("entityMappedBy")
    infix fun <LayerEntity, DomainModel> LayerEntity.mappedBy(
        mapper: DomainMapper<LayerEntity, DomainModel>,
    ): DomainModel = mapper.mapToDomainModel(this)
}
