package dev.kigya.mindplex.core.data.profile.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM userProfile WHERE id = :token LIMIT 1")
    suspend fun getProfile(token: String): UserLocalProfile?

    @Upsert
    suspend fun upsertProfile(profile: UserLocalProfile)

    @Delete
    suspend fun deleteProfile(profile: UserLocalProfile)

    @Query("SELECT score FROM userProfile WHERE id = :token LIMIT 1")
    suspend fun getScore(token: String): Int?

    @Query("UPDATE userProfile SET score = :newScore WHERE id = :token")
    suspend fun updateScore(
        token: String,
        newScore: Int,
    )
}
