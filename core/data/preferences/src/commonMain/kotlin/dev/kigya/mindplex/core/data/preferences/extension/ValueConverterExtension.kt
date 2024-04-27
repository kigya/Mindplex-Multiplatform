package dev.kigya.mindplex.core.data.preferences.extension

import dev.kigya.mindplex.core.data.preferences.converter.NonNullValueConverter
import dev.kigya.mindplex.core.data.preferences.converter.NonNullValueConverter.ErrorHandler
import dev.kigya.mindplex.core.data.preferences.converter.ValueConverter

/**
 * Converts the source [ValueConverter] to a [ValueConverter] with non-nullable types.
 * This method is short-hand for the [NonNullValueConverter].
 *
 * @param errorHandler The [NonNullValueConverter.ErrorHandler] to use for unexpected `null` values
 */
fun <ORIGINAL, CONVERTED> ValueConverter<ORIGINAL?, CONVERTED?>.nonNull(
    errorHandler: ErrorHandler<ORIGINAL, CONVERTED> = NonNullValueConverter.defaultErrorHandler(),
): ValueConverter<ORIGINAL, CONVERTED> = NonNullValueConverter(this, errorHandler)
