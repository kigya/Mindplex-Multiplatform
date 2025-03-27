package dev.kigya.mindplex.feature.leaderboard.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalPlace

@Dao
interface UserPlaceDao {
    @Query("SELECT * FROM user_place ORDER BY score DESC LIMIT 10")
    suspend fun getTopUsersByScore(): List<UserLocalPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserLocalPlace>)
}
