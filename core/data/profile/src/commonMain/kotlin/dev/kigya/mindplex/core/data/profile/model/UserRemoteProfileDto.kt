package dev.kigya.mindplex.core.data.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users.Document as UsersDocument

@Serializable
data class UserRemoteProfileDto(
    @SerialName(UsersDocument.AVATAR_URL) val avatarUrl: String,
    @SerialName(UsersDocument.COUNTRY_CODE) val countryCode: String?,
    @SerialName(UsersDocument.NAME) val name: String,
    @SerialName(UsersDocument.SCORE) val score: Int,
    @SerialName(UsersDocument.GLOBAL_RANK) val globalRank: Int,
    @SerialName(UsersDocument.LOCAL_RANK) val localRank: Int,
)
