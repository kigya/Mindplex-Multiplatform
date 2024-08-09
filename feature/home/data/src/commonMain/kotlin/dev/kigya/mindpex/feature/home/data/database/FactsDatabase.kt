package dev.kigya.mindpex.feature.home.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import dev.kigya.mindpex.feature.home.data.dao.FactsDao
import dev.kigya.mindpex.feature.home.data.model.FactLocal

expect object FactsDatabaseConstructor : RoomDatabaseConstructor<FactsDatabase>

@Database(
    entities = [FactLocal::class],
    version = 1,
)
@ConstructedBy(FactsDatabaseConstructor::class)
@TypeConverters(FactConverter::class)
abstract class FactsDatabase : RoomDatabase() {
    abstract val dao: FactsDao

    companion object {
        const val DATABASE_NAME = "cached_facts"
    }
}
