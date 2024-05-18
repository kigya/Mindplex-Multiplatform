package dev.kigya.mindplex.core.data.preferences.extension

import dev.kigya.mindplex.core.data.preferences.converter.NonNullValueConverter
import dev.kigya.mindplex.core.data.preferences.converter.ValueConverter
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueConverterExtensionTest {

    @Test
    fun `nonNull should handle non-null conversion correctly`() {
        // Given
        val nullableConverter = mockk<ValueConverter<String?, Int?>>()
        every { nullableConverter.toConverted("test") } returns 4
        every { nullableConverter.toOriginal(4) } returns "test"

        // When
        val nonNullableConverter = nullableConverter.nonNull()

        // Then
        assertEquals(
            expected = 4,
            actual = nonNullableConverter.toConverted("test"),
        )
        assertEquals(
            expected = "test",
            nonNullableConverter.toOriginal(4),
        )
    }

    @Test
    fun `nonNull should invoke error handler on null conversion results`() {
        // Given
        val nullableConverter = mockk<ValueConverter<String?, Int?>>()
        every { nullableConverter.toConverted("test") } returns null
        every { nullableConverter.toOriginal(4) } returns null

        val errorHandler = mockk<NonNullValueConverter.ErrorHandler<String, Int>>()
        every { errorHandler.onNullToConverted("test") } returns -1
        every { errorHandler.onNullToOriginal(4) } returns "default"

        // When
        val nonNullableConverter = nullableConverter.nonNull(errorHandler)

        // Then
        assertEquals(
            expected = -1,
            actual = nonNullableConverter.toConverted("test"),
        )
        assertEquals(
            expected = "default",
            actual = nonNullableConverter.toOriginal(4),
        )
    }
}
