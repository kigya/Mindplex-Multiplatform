package dev.kigya.mindplex.core.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users.Document as UsersDocument

@Serializable
internal data class GoogleRemoteUser(
    @SerialName(UsersDocument.AVATAR_URL) val avatarUrl: String,
    @SerialName(UsersDocument.NAME) val name: String,
)
