package dev.kigya.mindplex.feature.profile.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.profile.data.model.UserLocalProfile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_screen WHERE id = :token LIMIT 1")
    suspend fun getUserByToken(token: String): UserLocalProfile?

    @Upsert
    suspend fun upsertUser(user: UserLocalProfile)
}
