package dev.kigya.mindplex.feature.game.data.mapper

import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.game.data.model.local.DifficultyLocal
import dev.kigya.mindplex.feature.game.domain.model.DifficultyDomainModel
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

internal object DifficultyLocalMapper : DomainMapper<DifficultyLocal, DifficultyDomainModel>() {

    override fun mapToDomainModel(entity: DifficultyLocal): DifficultyDomainModel =
        outcomeCatching {
            DifficultyDomainModel.valueOf(entity.name)
        }.getOrDefault(DifficultyDomainModel.EASY)

    override fun mapFromDomainModel(domainModel: DifficultyDomainModel): DifficultyLocal =
        outcomeCatching {
            DifficultyLocal.valueOf(domainModel.name)
        }.getOrDefault(DifficultyLocal.EASY)
}
