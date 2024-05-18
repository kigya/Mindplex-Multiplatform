package dev.kigya.mindplex.konsistTest.domain

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import dev.kigya.mindplex.core.domain.interactor.base.BaseSuspendUseCase
import dev.kigya.mindplex.core.domain.interactor.base.BaseUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class UseCaseKonsistTest {
    private val useCases = Konsist
        .scopeFromProject()
        .classes()
        .filter { it.hasParentOf(BaseUseCase::class) || it.hasParentOf(BaseSuspendUseCase::class) }

    @Test
    fun `every use case should end with UseCase`() = useCases.forEach {
        if (!it.name.endsWith("UseCase")) {
            fail("Class does not follow naming convention: ${it.name}")
        }
    }

    @Test
    fun `every use case should be placed inside usecase package`() =
        useCases.assertTrue { koClass ->
            koClass.resideInPackage("dev.kigya.mindplex.feature..usecase")
        }

    @Test
    fun `every use case should contain only exactly one invoke function`() =
        useCases.assertTrue { koClass ->
            koClass.functions().size == 1 &&
                koClass.hasFunction { koFunction ->
                    koFunction.hasOperatorModifier &&
                        koFunction.hasOverrideModifier &&
                        koFunction.name == "invoke"
                }
        }

    @Test
    fun `every use case should be public`() =
        useCases.assertTrue(function = KoClassDeclaration::hasPublicOrDefaultModifier)
}
