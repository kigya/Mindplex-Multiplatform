package dev.kigya.mindplex.feature.profile.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.profile.data.model.UserLocalRank

@Dao
interface UserRankDao {
    @Query("SELECT * FROM userRank ORDER BY score DESC LIMIT :limit")
    suspend fun getTopUsersByScore(limit: Int): List<UserLocalRank>

    @Upsert
    suspend fun upsertUsers(users: List<UserLocalRank>)
}
