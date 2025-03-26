package dev.kigya.mindplex.feature.leaderboard.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.kigya.mindplex.feature.leaderboard.data.dao.UserPlaceDao
import dev.kigya.mindplex.feature.leaderboard.data.model.UserLocalPlace

expect object UserLocalPlaceConstructor : RoomDatabaseConstructor<UserPlaceDatabase>

@Database(
    entities = [UserLocalPlace::class],
    version = 1,
)
@ConstructedBy(UserLocalPlaceConstructor::class)
abstract class UserPlaceDatabase : RoomDatabase() {
    abstract val dao: UserPlaceDao

    companion object {
        const val DATABASE_NAME = "user_place"
    }
}
