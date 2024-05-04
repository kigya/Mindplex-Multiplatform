package dev.kigya.mindplex.core.data.preferences.converter

import org.junit.Assert.assertArrayEquals
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertContentEquals

class Base64ValueConverterTest {

    private val converter = Base64ValueConverter()

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `toConverted should return a Base64 string from a byte array`() {
        // Given
        val byteArray = "hello world".toByteArray()

        // When
        val base64String = converter.toConverted(byteArray)

        // Then
        assertEquals(
            message = "Base64 conversion failed",
            expected = Base64.encode(byteArray),
            actual = base64String,
        )
    }

    @Test
    fun `toConverted should return null when input is null`() {
        // When
        val base64String = converter.toConverted(null)

        // Then
        assertEquals(
            message = "Conversion of null should return null",
            expected = null,
            actual = base64String,
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `toOriginal should return a byte array from a Base64 string`() {
        // Given
        val base64String = Base64.encode("hello world".toByteArray())

        // When
        val byteArray = converter.toOriginal(base64String)

        // Then
        assertContentEquals(
            message = "Base64 decoding failed",
            expected = "hello world".toByteArray(),
            actual = byteArray,
        )
    }

    @Test
    fun `toOriginal should return null when input is null`() {
        // When
        val byteArray = converter.toOriginal(null)

        // Then
        assertEquals(
            message = "Decoding of null should return null",
            expected = null,
            actual = byteArray,
        )
    }
}
