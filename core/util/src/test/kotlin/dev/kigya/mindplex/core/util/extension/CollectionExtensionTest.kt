package dev.kigya.mindplex.core.util.extension

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class CollectionExtensionTest {

    @Test
    fun `ifNotEmpty should execute block if collection is not empty`() {
        // Given
        val list = listOf("Hello", "World")
        var wasBlockExecuted = false

        // When
        list.ifNotEmpty {
            wasBlockExecuted = true
        }

        // Then
        assertTrue(
            actual = wasBlockExecuted,
            message = "Block should be executed for non-empty collection",
        )
    }

    @Test
    fun `ifNotEmpty should not execute block and return itself if collection is empty`() {
        // Given
        val emptyList = emptyList<String>()
        var wasBlockExecuted = false

        // When
        val result = emptyList.ifNotEmpty {
            wasBlockExecuted = true
        }

        // Then
        assertFalse(
            actual = wasBlockExecuted,
            message = "Block should not be executed for an empty collection",
        )
        assertSame(
            expected = result,
            actual = emptyList,
            message = "Function should return the original collection when it's empty",
        )
    }
}
