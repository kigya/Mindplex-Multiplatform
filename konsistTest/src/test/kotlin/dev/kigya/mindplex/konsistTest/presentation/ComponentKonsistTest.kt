package dev.kigya.mindplex.konsistTest.presentation

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import dev.kigya.mindplex.core.presentation.feature.component.BaseComponent
import dev.kigya.mindplex.core.presentation.feature.component.UnidirectionalComponentContract
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ComponentKonsistTest {

    private val components = Konsist
        .scopeFromProject()
        .classes()
        .filter { it.hasParentOf(BaseComponent::class) }

    @Test
    fun `every component should end with Component`() = components.forEach {
        if (!it.name.endsWith("Component")) {
            fail("Class does not follow naming convention: ${it.name}")
        }
    }

    @Test
    fun `every component constructor should have componentContext dependency name`() =
        components.assertTrue { koClass ->
            koClass.primaryConstructor?.hasParameterWithName("componentContext") ?: false
        }

    @Test
    fun `every component constructor should have ComponentContext dependency type`() =
        components.assertTrue { koClass ->
            koClass.primaryConstructor?.hasParameter { koParam ->
                koParam.hasTypeOf(ComponentContext::class)
            }
        }

    @Test
    fun `every component should have public access modifier`() =
        components.assertTrue(function = KoClassDeclaration::hasPublicOrDefaultModifier)

    @Test
    fun `every component implements a feature contract`() = components.assertTrue { koClass ->
        koClass.hasParentInterface { koInterface ->
            koInterface.hasParentInterfaceOf(UnidirectionalComponentContract::class)
        }
    }

    @Test
    fun `every component overrides handleEvent function`() = components.assertTrue { koClass ->
        koClass.hasFunction { koFunction ->
            koFunction.hasOverrideModifier && koFunction.hasNameContaining("handleEvent")
        }
    }

    @Test
    fun `every component should be placed inside component package`() =
        components.assertTrue { koClass ->
            koClass.resideInPackage("dev.kigya.mindplex.feature..component")
        }

    @Test
    fun `every component should have @Stable annotation`() = components.assertTrue { koClass ->
        koClass.hasAnnotationOf(Stable::class)
    }
}
