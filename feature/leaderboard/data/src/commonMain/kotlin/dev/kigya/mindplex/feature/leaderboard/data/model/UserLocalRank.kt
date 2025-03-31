package dev.kigya.mindplex.feature.leaderboard.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.kigya.mindplex.core.util.extension.empty

@Entity(tableName = "user_rank")
data class UserLocalRank(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @Embedded val userLocalData: UserLocalData? = null,
)

data class UserLocalData(
    @ColumnInfo("name") val name: String = String.empty,
    @ColumnInfo("avatar_url") val avatar: String = String.empty,
    @ColumnInfo("country_code") val countryCode: String = String.empty,
    @ColumnInfo("score") val score: Int = 0,
)
