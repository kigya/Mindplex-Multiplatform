package dev.kigya.mindplex.core.data.preferences.converter

/**
 * A [ValueConverter] that acts as a pass-through without performing any
 * conversion.
 */
class NoOpValueConverter<T> : ValueConverter<T, T> {
    override fun toConverted(originalValue: T): T = originalValue
    override fun toOriginal(convertedValue: T): T = convertedValue
}
