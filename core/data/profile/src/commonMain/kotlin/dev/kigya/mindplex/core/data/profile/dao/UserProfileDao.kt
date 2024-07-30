package dev.kigya.mindplex.core.data.profile.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = :token LIMIT 1")
    suspend fun get(token: String): UserLocalProfile?

    @Upsert
    suspend fun upsert(profile: UserLocalProfile)

    @Delete
    suspend fun delete(profile: UserLocalProfile)
}
