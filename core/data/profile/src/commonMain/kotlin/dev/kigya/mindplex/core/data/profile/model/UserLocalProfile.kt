package dev.kigya.mindplex.core.data.profile.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userProfile")
data class UserLocalProfile(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @Embedded val userLocalData: UserLocalData? = null,
)

data class UserLocalData(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("avatarUrl") val avatar: String,
    @ColumnInfo("countryCode") val countryCode: String,
    @ColumnInfo("score") val score: Int,
    @ColumnInfo("globalRank") val globalRank: Int,
    @ColumnInfo("localRank") val localRank: Int,
)
