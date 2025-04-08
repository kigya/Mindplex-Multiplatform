package dev.kigya.mindplex.core.data.credentials.model

import dev.kigya.mindplex.core.data.firebase.FirestoreConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiKeyRemoteDto(
    @SerialName(FirestoreConfig.Collection.Secrets.Document.FlagsAPI.FLAGS_KEY)
    val flagsKey: String,
)
