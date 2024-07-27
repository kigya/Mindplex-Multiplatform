package dev.kigya.mindplex.feature.login.presentation.mapper

import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.domain.interactor.model.MindplexDomainError
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

internal fun GoogleUser?.toDomain() = this?.let {
    GoogleUserSignInDomainModel(
        tokenId = this.idToken,
        displayName = this.displayName,
        profilePictureUrl = this.profilePicUrl,
    )
}

internal fun MindplexDomainError.toStubErrorType() = if (this == MindplexDomainError.NETWORK) {
    StubErrorType.NETWORK
} else {
    StubErrorType.UNSPECIFIED
}
