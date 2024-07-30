package dev.kigya.mindplex.di.internal.module

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.kigya.mindplex.core.data.profile.database.UserProfileDatabase
import dev.kigya.mindplex.core.data.profile.database.instantiateImpl
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val databaseModule = module {
    single {
        Room.databaseBuilder<UserProfileDatabase>(
            name = NSHomeDirectory() + "/${UserProfileDatabase.DATABASE_NAME}.db",
            factory = { UserProfileDatabase::class.instantiateImpl() },
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
