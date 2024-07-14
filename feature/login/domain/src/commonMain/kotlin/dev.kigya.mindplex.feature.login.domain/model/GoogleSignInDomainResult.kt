package dev.kigya.mindplex.feature.login.domain.model

import androidx.annotation.VisibleForTesting
import kotlin.jvm.JvmInline

sealed interface GoogleSignInDomainResult {
    enum class GoogleSignInDomainFailureReason {
        NETWORK,
        OTHER,
    }

    data object Success : GoogleSignInDomainResult

    @JvmInline
    @VisibleForTesting
    value class Failure(
        val reason: GoogleSignInDomainFailureReason,
    ) : GoogleSignInDomainResult
}
