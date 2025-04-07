package dev.kigya.mindplex.feature.profile.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.kigya.mindplex.feature.profile.data.dao.UserRankDao
import dev.kigya.mindplex.feature.profile.data.model.UserLocalRank

expect object UserLocalRankConstructor : RoomDatabaseConstructor<UserRankDatabase>

@Database(
    entities = [UserLocalRank::class],
    version = 1,
)
@ConstructedBy(UserLocalRankConstructor::class)
abstract class UserRankDatabase : RoomDatabase() {
    abstract val dao: UserRankDao

    companion object {
        const val DATABASE_NAME = "user_rank"
    }
}
