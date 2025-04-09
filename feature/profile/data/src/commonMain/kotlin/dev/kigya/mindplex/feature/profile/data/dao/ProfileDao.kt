package dev.kigya.mindplex.feature.profile.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.profile.data.model.LocalProfile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_screen WHERE id = :token LIMIT 1")
    suspend fun getUserByToken(token: String): LocalProfile?

    @Upsert
    suspend fun upsertUser(user: LocalProfile)
}
