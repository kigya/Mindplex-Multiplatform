package dev.kigya.mindplex.feature.game.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TypeDto {

    @SerialName("boolean")
    BOOLEAN,

    @SerialName("multiple")
    MULTIPLE,
}
