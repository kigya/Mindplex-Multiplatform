package dev.kigya.mindplex.core.data.profile.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.kigya.mindplex.core.util.extension.empty

@Entity(tableName = "user_profile")
data class UserLocalProfile(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @Embedded val userLocalData: UserLocalData? = null,
)

data class UserLocalData(
    @ColumnInfo("name") val name: String = String.empty,
    @ColumnInfo("avatar_url") val avatar: String = String.empty,
)
