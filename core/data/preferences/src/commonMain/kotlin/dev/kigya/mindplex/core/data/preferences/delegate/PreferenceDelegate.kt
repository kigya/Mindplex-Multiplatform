package dev.kigya.mindplex.core.data.preferences.delegate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.core.data.preferences.converter.NoOpValueConverter
import dev.kigya.mindplex.core.data.preferences.converter.ValueConverter
import dev.kigya.mindplex.core.data.preferences.extension.getOrDefault
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A Kotlin delegate to read and write values in a [Preferences] [DataStore].
 * For example
 * ```kotlin
 * var theme by dataStore.value("uiTheme", Theme.FOLLOW_SYSTEM)
 * ```
 *
 * **NOTE:**
 * Reading a writing [DataStore] values can potentially be slower than expected and it is recommended
 * to perform reads/writes (get/set) on threads other than Main/UI.
 *
 * @param key The unique id used to store and retrieve the preference, typically this is a
 *            descriptive id such as "uiTheme"
 * @param defaultValue The value to return when [key] isn't contained by the [DataStore]
 */
inline fun <reified T> DataStore<Preferences>.flowPreference(
    key: String,
    defaultValue: T,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
): ReadWriteProperty<Any, Flow<T>> = flowPreference(
    key = getPreferencesKey(key),
    defaultValue = defaultValue,
    converter = NoOpValueConverter(),
    coroutineContext = coroutineContext,
)

/**
 * A Kotlin delegate to read and write values in a [Preferences] [DataStore] with value transformations.
 * For example
 * ```kotlin
 * var theme by dataStore.value("uiTheme", Theme.FOLLOW_SYSTEM, EnumValueConverter(Theme::class))
 * ```
 *
 * **NOTE:**
 * Reading a writing [DataStore] values can potentially be slower than expected and it is recommended
 * to perform reads/writes (get/set) on threads other than Main/UI.
 *
 * @param key The [Preferences.Key] used to store and retrieve the preference
 * @param defaultValue The value to return when [key] isn't contained by the [DataStore]
 * @param converter The [ValueConverter] to convert between the use type (e.g. an Enum)
 *                    and a format that the can be stored in preferences. Supported preference types
 *                    are `Int`, `Double`, `String`, `Boolean`, `Float`, and `Long`.
 */
fun <T, S> DataStore<Preferences>.flowPreference(
    key: Preferences.Key<S>,
    defaultValue: T,
    converter: ValueConverter<T, S>,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
): ReadWriteProperty<Any, Flow<T>> {
    val defaultStoreValue = converter.toConverted(defaultValue)

    return retrieveDataStoreProperty(
        valueSetter = { store, value ->
            store.toMutablePreferences().apply {
                this[key] = value
            }
        },
        valueGetter = { store ->
            store.getOrDefault(
                key = key,
                defaultValue = defaultStoreValue,
            )
        },
        converter = converter,
        coroutineContext = coroutineContext,
    )
}

/**
 * A Kotlin delegate to read and write values in a [DataStore] using Flow.
 * For example
 * ```kotlin
 * var theme by dataStore.retrieveDataStoreProperty("uiTheme", Theme.FOLLOW_SYSTEM)
 * ```
 *
 * **NOTE:**
 * Reading and writing [DataStore] values can be slower than expected and it is recommended
 * to perform reads/writes (get/set) on threads other than Main/UI.
 *
 * @param valueGetter The function that handles retrieving the value from the [DataStore] of [DS]
 * @param valueSetter The function that handles setting the value in the [DataStore] of [DS]
 * @param converter The [ValueConverter] to convert between the use type [T] (e.g. an Enum)
 *                    and a format that can be stored in preferences [S]. Supported preference types
 *                    are `Int`, `Double`, `String`, `Boolean`, `Float`, and `Long`.
 */
internal fun <T, S, DS> DataStore<DS>.retrieveDataStoreProperty(
    valueSetter: suspend (store: DS, value: S) -> DS,
    valueGetter: suspend (store: DS) -> S,
    converter: ValueConverter<T, S>,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
): ReadWriteProperty<Any, Flow<T>> {
    val currentFlow = MutableSharedFlow<T>(replay = 1)

    fun launchAutoCloseableDataStoreOperation(block: suspend CoroutineScope.() -> Unit) {
        val scope = CoroutineScope(coroutineContext)
        scope.launch {
            try {
                block()
            } finally {
                scope.cancel()
            }
        }
    }

    return object : ReadWriteProperty<Any, Flow<T>> {
        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Flow<T>,
        ) = launchAutoCloseableDataStoreOperation {
            value.collect { newValue ->
                updateData { store ->
                    valueSetter(store, converter.toConverted(newValue))
                }
            }
        }

        override fun getValue(
            thisRef: Any,
            property: KProperty<*>,
        ): Flow<T> {
            launchAutoCloseableDataStoreOperation {
                val data = data.first()
                currentFlow.emit(converter.toOriginal(valueGetter(data)))
            }
            return currentFlow
        }
    }
}

/**
 * Retrieves a [Preferences.Key] for the [key] associated with a value of type [T].
 * This supports all [Preferences.Key] types except the type `Set<String>`.
 *
 * @return A [Preferences.Key] for the type [T]
 * @throws IllegalArgumentException When the type [T] isn't a supported [Preferences.Key] type
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> getPreferencesKey(key: String): Preferences.Key<T> =
    when (T::class) {
        Int::class -> intPreferencesKey(key) as Preferences.Key<T>
        Double::class -> doublePreferencesKey(key) as Preferences.Key<T>
        String::class -> stringPreferencesKey(key) as Preferences.Key<T>
        Boolean::class -> booleanPreferencesKey(key) as Preferences.Key<T>
        Float::class -> floatPreferencesKey(key) as Preferences.Key<T>
        Long::class -> longPreferencesKey(key) as Preferences.Key<T>
        else -> throw IllegalArgumentException(
            "Preference type of \"${T::class}\" is not supported, must be one of Int, Double, String, Boolean, Float, or Long",
        )
    }

