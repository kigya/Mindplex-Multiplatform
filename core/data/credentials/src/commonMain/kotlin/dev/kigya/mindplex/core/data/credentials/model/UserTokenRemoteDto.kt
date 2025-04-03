package dev.kigya.mindplex.core.data.credentials.model

import dev.kigya.mindplex.core.data.firebase.FirestoreConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserTokenRemoteDto(
    @SerialName(FirestoreConfig.Collection.Secrets.Document.UserToken.TOKEN)
    val token: String,
)
