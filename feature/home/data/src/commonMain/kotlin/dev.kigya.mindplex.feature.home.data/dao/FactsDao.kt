package dev.kigya.mindplex.feature.home.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.home.data.model.FactLocal

@Dao
interface FactsDao {
    @Query("SELECT * FROM facts")
    suspend fun get(): List<FactLocal?>

    @Upsert
    suspend fun upsert(facts: List<FactLocal>)

    @Query("DELETE FROM facts")
    suspend fun deleteAll()
}
