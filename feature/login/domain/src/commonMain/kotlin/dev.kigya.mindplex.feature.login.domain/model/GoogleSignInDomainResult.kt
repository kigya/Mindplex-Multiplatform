package dev.kigya.mindplex.feature.login.domain.model

import kotlin.jvm.JvmInline

sealed interface GoogleSignInDomainResult {
    enum class GoogleSignInDomainFailureReason {
        NETWORK,
        OTHER,
    }

    data object Success : GoogleSignInDomainResult

    @JvmInline
    value class Failure internal constructor(
        val reason: GoogleSignInDomainFailureReason,
    ) : GoogleSignInDomainResult
}
