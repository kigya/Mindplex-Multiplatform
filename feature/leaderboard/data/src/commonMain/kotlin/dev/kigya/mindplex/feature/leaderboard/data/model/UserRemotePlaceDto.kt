package dev.kigya.mindplex.feature.leaderboard.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import dev.kigya.mindplex.core.data.firebase.FirestoreConfig.Collection.Users.Document as UsersDocument

@Serializable
internal data class UserRemotePlaceDto(
    @SerialName(UsersDocument.AVATAR_URL) val avatarUrl: String,
    @SerialName(UsersDocument.NAME) val name: String,
    @SerialName(UsersDocument.SCORE) val score: Int,
)
