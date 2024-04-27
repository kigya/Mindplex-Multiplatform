package dev.kigya.mindplex.core.data.preferences.converter

/**
 * Defines the contract used to convert from an application object
 * to an object that can be stored.
 */
interface ValueConverter<ORIGINAL, CONVERTED> {
    fun toConverted(originalValue: ORIGINAL): CONVERTED
    fun toOriginal(convertedValue: CONVERTED): ORIGINAL
}
