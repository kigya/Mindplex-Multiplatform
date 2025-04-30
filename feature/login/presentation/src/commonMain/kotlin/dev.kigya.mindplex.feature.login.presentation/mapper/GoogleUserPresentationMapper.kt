package dev.kigya.mindplex.feature.login.presentation.mapper

import com.mmk.kmpauth.google.GoogleUser
import dev.kigya.mindplex.core.util.mapper.DomainMapper
import dev.kigya.mindplex.feature.login.domain.model.GoogleUserSignInDomainModel

internal object GoogleUserPresentationMapper : DomainMapper<GoogleUser?, GoogleUserSignInDomainModel?>() {

    override fun mapToDomainModel(entity: GoogleUser?): GoogleUserSignInDomainModel? = entity?.let {
        GoogleUserSignInDomainModel(
            idToken = it.idToken,
            displayName = it.displayName,
            profilePictureUrl = it.profilePicUrl,
        )
    }
}
