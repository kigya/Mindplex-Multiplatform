package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.local.DifficultyLocal
import dev.kigya.mindplex.feature.game.domain.model.DifficultyDomainModel

internal object DifficultyLocalMapper : DomainMapper<DifficultyLocal, DifficultyDomainModel>() {

    override fun mapToDomainModel(entity: DifficultyLocal): DifficultyDomainModel = runCatching {
        DifficultyDomainModel.valueOf(entity.name)
    }.getOrDefault(DifficultyDomainModel.EASY)

    override fun mapFromDomainModel(domainModel: DifficultyDomainModel): DifficultyLocal =
        runCatching {
            DifficultyLocal.valueOf(domainModel.name)
        }.getOrDefault(DifficultyLocal.EASY)
}
