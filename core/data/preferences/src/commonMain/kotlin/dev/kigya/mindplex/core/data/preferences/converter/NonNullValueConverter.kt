package dev.kigya.mindplex.core.data.preferences.converter

/**
 * A [ValueConverter] that ensures that the delegated [ValueConverter] doesn't return
 * `null` values otherwise an exception will be thrown.
 *
 * @param
 */
class NonNullValueConverter<ORIGINAL, CONVERTED>(
    private val source: ValueConverter<ORIGINAL?, CONVERTED?>,
    private val errorHandler: ErrorHandler<ORIGINAL, CONVERTED> = defaultErrorHandler(),
) : ValueConverter<ORIGINAL, CONVERTED> {

    override fun toConverted(originalValue: ORIGINAL): CONVERTED =
        source.toConverted(originalValue) ?: errorHandler.onNullToConverted(originalValue)

    override fun toOriginal(convertedValue: CONVERTED): ORIGINAL =
        source.toOriginal(convertedValue) ?: errorHandler.onNullToOriginal(convertedValue)

    /**
     * Defines how [NonNullValueConverter] should handle unexpected `null` values.
     */
    interface ErrorHandler<ORIGINAL, CONVERTED> {
        fun onNullToConverted(originalValue: ORIGINAL): CONVERTED
        fun onNullToOriginal(convertedValue: CONVERTED): ORIGINAL
    }

    /**
     * An implementation of [ErrorHandler] that throws an [IllegalArgumentException] when
     * any error occurs.
     */
    class DefaultErrorHandler : ErrorHandler<Any?, Any?> {
        override fun onNullToConverted(originalValue: Any?): Any? {
            throw IllegalArgumentException("Unable to convert from value $originalValue, result was `null`")
        }

        override fun onNullToOriginal(convertedValue: Any?): Any? {
            throw IllegalArgumentException("Unable to convert from value $convertedValue, result was `null`")
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <ORIGINAL, CONVERTED> defaultErrorHandler(): ErrorHandler<ORIGINAL, CONVERTED> =
            DefaultErrorHandler() as ErrorHandler<ORIGINAL, CONVERTED>
    }
}
