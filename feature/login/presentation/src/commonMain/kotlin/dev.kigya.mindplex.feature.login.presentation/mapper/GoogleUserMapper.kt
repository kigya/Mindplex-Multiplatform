package dev.kigya.mindplex.feature.login.presentation.mapper

import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

internal fun GoogleUser?.toDomain() = this?.let {
    GoogleUserSignInDomainModel(
        tokenId = this.idToken,
        displayName = this.displayName,
        profilePictureUrl = this.profilePicUrl,
    )
}
