package dev.kigya.mindplex.di.provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath

@Suppress("LateinitUsage")
private lateinit var dataStore: DataStore<Preferences>

@OptIn(InternalCoroutinesApi::class)
private val lock = SynchronizedObject()

/**
 * Gets the [DataStore] instance.
 *
 * @param producePath function to produce the path to the data store file
 *
 * @return the [DataStore] instance
 */
@OptIn(InternalCoroutinesApi::class)
fun getDataStore(producePath: () -> String): DataStore<Preferences> = synchronized(lock) {
    if (::dataStore.isInitialized) {
        dataStore
    } else {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() },
        ).also { dataStore = it }
    }
}

internal const val DATA_STORE_FILE_NAME = "mindplex_settings.preferences_pb"
