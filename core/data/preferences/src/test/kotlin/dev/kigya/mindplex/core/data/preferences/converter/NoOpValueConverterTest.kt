package dev.kigya.mindplex.core.data.preferences.converter

import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class NoOpValueConverterTest {

    private val converter = NoOpValueConverter<Any>()

    @Test
    fun `toConverted should return the same object it receives`() {
        // Given
        val originalObject = Any()

        // When
        val convertedObject = converter.toConverted(originalObject)

        // Then
        assertEquals(
            message = "toConverted should return the input object without modification",
            expected = originalObject,
            actual = convertedObject,
        )
    }

    @Test
    fun `toOriginal should return the same object it receives`() {
        // Given
        val convertedObject = Any()

        // When
        val originalObject = converter.toOriginal(convertedObject)

        // Then
        assertEquals(
            message = "toOriginal should return the input object without modification",
            expected = convertedObject,
            actual = originalObject,
        )
    }
}
