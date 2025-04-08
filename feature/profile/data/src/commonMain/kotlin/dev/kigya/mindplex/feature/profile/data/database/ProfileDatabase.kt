package dev.kigya.mindplex.feature.profile.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.kigya.mindplex.feature.profile.data.dao.ProfileDao
import dev.kigya.mindplex.feature.profile.data.model.UserLocalProfile

expect object LocalProfileConstructor : RoomDatabaseConstructor<ProfileDatabase>

@Database(
    entities = [UserLocalProfile::class],
    version = 1,
)
@ConstructedBy(LocalProfileConstructor::class)
abstract class ProfileDatabase : RoomDatabase() {
    abstract val dao: ProfileDao

    companion object {
        const val DATABASE_NAME = "profile_screen"
    }
}
