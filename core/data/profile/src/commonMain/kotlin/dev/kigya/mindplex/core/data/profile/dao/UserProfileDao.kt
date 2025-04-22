package dev.kigya.mindplex.core.data.profile.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM userProfile WHERE id = :userId LIMIT 1")
    suspend fun getProfile(userId: String): UserLocalProfile?

    @Upsert
    suspend fun upsertProfile(profile: UserLocalProfile)

    @Delete
    suspend fun deleteProfile(profile: UserLocalProfile)

    @Query("SELECT score FROM userProfile WHERE id = :userId LIMIT 1")
    suspend fun getScore(userId: String): Int?

    @Query("UPDATE userProfile SET score = :newScore WHERE id = :userId")
    suspend fun updateScore(
        userId: String,
        newScore: Int,
    )
}
