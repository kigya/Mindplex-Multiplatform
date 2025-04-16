package dev.kigya.mindplex.core.data.profile.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.kigya.mindplex.core.data.profile.dao.UserProfileDao
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile

expect object UserLocalProfileConstructor : RoomDatabaseConstructor<UserProfileDatabase>

@Database(
    entities = [UserLocalProfile::class],
    version = 1,
)
@ConstructedBy(UserLocalProfileConstructor::class)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract val dao: UserProfileDao

    companion object {
        const val DATABASE_NAME = "userProfile"
    }
}
