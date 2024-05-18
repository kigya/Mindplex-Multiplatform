package dev.kigya.mindplex.konsistTest.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class ArchitectureKonsistTest {

    private val presentation = Layer("Presentation", "dev.kigya.mindplex..presentation..")
    private val domain = Layer("Domain", "dev.kigya.mindplex..domain..")
    private val data = Layer("Data", "dev.kigya.mindplex..data..")

    @Test
    fun `every feature should follow dependency rule`() = Konsist
        .scopeFromProject()
        .assertArchitecture {
            presentation.dependsOn(domain)
            data.dependsOn(domain)
            domain.dependsOnNothing()
        }
}
