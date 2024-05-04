package dev.kigya.mindplex.core.data.preferences.converter

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertThrows
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class NonNullValueConverterTest {

    private val mockConverter = mockk<ValueConverter<String?, Int?>>()
    private val nonNullConverter = NonNullValueConverter(mockConverter)

    @Test
    fun `toConverted should return non-null value when underlying converter succeeds`() {
        // Given
        val originalValue = "hello"
        every { mockConverter.toConverted(originalValue) } returns originalValue.length

        // When
        val convertedValue = nonNullConverter.toConverted(originalValue)

        // Then
        assertEquals(
            message = "Conversion should return the length of the string",
            expected = 5,
            actual = convertedValue
        )
    }

    @Test
    fun `toConverted should invoke error handler when underlying converter returns null`() {
        // Given
        val originalValue = "hello"
        every { mockConverter.toConverted(originalValue) } returns null

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            nonNullConverter.toConverted(originalValue)
        }

        // Then
        assertEquals(
            message = "Expected exception message to indicate conversion issue",
            expected = "Unable to convert from value $originalValue, result was `null`",
            actual = exception.message,
        )
    }

    @Test
    fun `toOriginal should return non-null value when underlying converter succeeds`() {
        // Given
        val convertedValue = 5
        every { mockConverter.toOriginal(convertedValue) } returns convertedValue.toString()

        // When
        val originalValue = nonNullConverter.toOriginal(convertedValue)

        // Then
        assertEquals(
            message = "Conversion should return the string representation of the number",
            expected = "5",
            actual = originalValue,
        )
    }

    @Test
    fun `toOriginal should invoke error handler when underlying converter returns null`() {
        // Given
        val convertedValue = 5
        every { mockConverter.toOriginal(convertedValue) } returns null

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            nonNullConverter.toOriginal(convertedValue)
        }

        assertEquals(
            message = "Expected exception message to indicate conversion issue",
            expected = "Unable to convert from value $convertedValue, result was `null`",
            actual = exception.message,
        )
    }
}
