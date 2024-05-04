package dev.kigya.mindplex.core.util.extension

import kotlin.test.Test
import kotlin.test.assertTrue

class StringExtensionTest {

    @Test
    fun `string empty extension should produce an empty string`() {
        // Given
        val emptyString = String.empty

        // Then
        assertTrue(
            actual = emptyString.isEmpty(),
            message = "The String.empty should return an empty string",
        )
    }
}
