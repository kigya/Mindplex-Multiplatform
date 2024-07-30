package dev.kigya.mindplex.core.data.profile.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kigya.mindplex.core.data.profile.dao.UserProfileDao
import dev.kigya.mindplex.core.data.profile.model.UserLocalProfile

@Database(
    entities = [UserLocalProfile::class],
    version = 1,
)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract val dao: UserProfileDao

    companion object {
        const val DATABASE_NAME = "user_profile"
    }
}
