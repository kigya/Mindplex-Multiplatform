package dev.kigya.mindplex.konsist.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import org.junit.jupiter.api.Test

internal class ScreenKonsistTest {
    private val screens = Konsist
        .scopeFromProject()
        .functions()
        .filter { it.hasAnnotationOf(Composable::class) && it.hasNameEndingWith("Screen") }

    @Test
    fun `every feature screen should be placed inside ui package`() =
        screens.assertTrue { koFunction ->
            koFunction.resideInPackage("dev.kigya.mindplex.feature..ui")
        }

    @Test
    fun `every feature screen should have public access modifier`() =
        screens.assertTrue { koFunction ->
            koFunction.hasPublicOrDefaultModifier
        }

    @Test
    fun `every feature screen should have contract param`() = screens.assertTrue { koFunction ->
        koFunction.hasParameter { koParameter ->
            koParameter.hasType { koType ->
                koType.hasInterfaceDeclaration { koInterfaceDeclaration ->
                    koInterfaceDeclaration.hasParentInterfaceOf(UnidirectionalViewModelContract::class)
                }
            }
        }
    }

    @Test
    fun `every feature screen should be a top-level function`() =
        screens.assertTrue(function = KoFunctionDeclaration::isTopLevel)

    @Test
    fun `every feature screen should have a companion internal content function`() =
        screens.assertTrue { koScreenFunction ->
            koScreenFunction.containingFile.hasFunction { koFunction ->
                koFunction.hasNameEndingWith("Content") &&
                    koFunction.hasAnnotationOf(Composable::class) &&
                    koFunction.hasInternalModifier &&
                    koFunction.hasAnnotationOf(VisibleForTesting::class)
            }
        }
}
