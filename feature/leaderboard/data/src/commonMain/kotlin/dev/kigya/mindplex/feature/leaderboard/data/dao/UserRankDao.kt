package dev.kigya.mindplex.feature.leaderboard.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalRank

@Dao
interface UserRankDao {
    @Query("SELECT * FROM user_rank ORDER BY score DESC LIMIT 10")
    suspend fun getTopUsersByScore(): List<UserLocalRank>

    @Upsert
    suspend fun upsertUsers(users: List<UserLocalRank>)
}
