package dev.kigya.mindplex.feature.profile.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_screen")
data class LocalProfile(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @Embedded val profileLocalData: ProfileLocalData? = null,
)

data class ProfileLocalData(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("avatar_url") val avatar: String,
    @ColumnInfo("country_code") val countryCode: String,
    @ColumnInfo("score") val score: Int,
    @ColumnInfo("globalRank") val globalRank: Int,
    @ColumnInfo("localRank") val localRank: Int,
)
