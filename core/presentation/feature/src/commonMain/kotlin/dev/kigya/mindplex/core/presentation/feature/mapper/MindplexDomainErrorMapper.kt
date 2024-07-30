package dev.kigya.mindplex.core.presentation.feature.mapper

import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.presentation.component.StubErrorType

fun MindplexDomainError.toStubErrorType() = if (this == MindplexDomainError.NETWORK) {
    StubErrorType.NETWORK
} else {
    StubErrorType.UNSPECIFIED
}
