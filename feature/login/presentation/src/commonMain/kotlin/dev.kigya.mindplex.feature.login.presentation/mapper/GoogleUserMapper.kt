package dev.kigya.mindplex.feature.login.presentation.mapper

import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.Failure
import dev.kigya.mindplex.feature.login.domain.model.GoogleSignInDomainResult.GoogleSignInDomainFailureReason
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserDomainModel

internal fun GoogleUser?.toDomain() = this?.let {
    GoogleUserDomainModel(
        tokenId = this.idToken,
        displayName = this.displayName,
        profilePictureUrl = this.profilePicUrl,
    )
}

internal fun Failure.toStubErrorType() =
    if (this.reason == GoogleSignInDomainFailureReason.NETWORK) {
        StubErrorType.NETWORK
    } else {
        StubErrorType.UNSPECIFIED
    }
