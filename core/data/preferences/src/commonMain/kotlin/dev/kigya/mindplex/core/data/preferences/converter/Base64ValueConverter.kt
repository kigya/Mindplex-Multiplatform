package dev.kigya.mindplex.core.data.preferences.converter

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * A [ValueConverter] that converts the incoming value to a Base64 string.
 */
class Base64ValueConverter : ValueConverter<ByteArray?, String?> {
    @OptIn(ExperimentalEncodingApi::class)
    override fun toConverted(originalValue: ByteArray?): String? =
        originalValue?.let(Base64::encode)

    @OptIn(ExperimentalEncodingApi::class)
    override fun toOriginal(convertedValue: String?): ByteArray? =
        convertedValue?.let(Base64::decode)
}
