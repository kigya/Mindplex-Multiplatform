package dev.kigya.mindplex.core.data.preferences.extension

import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.core.data.preferences.converter.NoOpValueConverter
import dev.kigya.mindplex.core.data.preferences.converter.ValueConverter

/**
 * Retrieves the [Preferences] value associated with the [key],
 * if no value is defined for [key] in [Preferences] then the
 * [defaultValue] will be returned.
 *
 * @param key The [Preferences.Key] used to retrieve the [Preferences] value
 * @param defaultValue The value to return when [key] isn't defined in [Preferences]
 */
internal fun <T> Preferences.getOrDefault(key: Preferences.Key<T>, defaultValue: T): T =
    getOrDefault(
        key = key,
        defaultValue = defaultValue,
        converter = NoOpValueConverter(),
    )

/**
 * Retrieves the [Preferences] value associated with the [key],
 * if no value is defined for [key] in [Preferences] then the
 * [defaultValue] will be returned.
 *
 * @param key The [Preferences.Key] used to retrieve the [Preferences] value
 * @param defaultValue The value to return when [key] isn't defined in [Preferences]
 * @param converter The [ValueConverter] used to convert between the stored type [S]
 *                  and the expected read/write type [T]
 */
@Suppress("UNCHECKED_CAST")
internal fun <T, S> Preferences.getOrDefault(
    key: Preferences.Key<S>,
    defaultValue: T,
    converter: ValueConverter<T, S>,
): T {
    if (contains(key)) {
        return get(key)?.let { converter.toOriginal(it) } as T
    }

    return defaultValue
}
