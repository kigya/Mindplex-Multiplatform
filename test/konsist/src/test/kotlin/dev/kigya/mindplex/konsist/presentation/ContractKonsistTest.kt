package dev.kigya.mindplex.konsist.presentation

import androidx.compose.runtime.Immutable
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import org.junit.jupiter.api.Test

internal class ContractKonsistTest {
    private val contracts = Konsist
        .scopeFromProject()
        .interfaces()
        .filter { it.hasParentInterfaceOf(UnidirectionalViewModelContract::class) }

    @Test
    fun `every feature contract should have @Immutable data class State inherited from CopyableComponentState`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.hasClass { koClass ->
                koClass.hasDataModifier &&
                    koClass.name == "State" &&
                    koClass.hasAnnotationOf(Immutable::class) &&
                    koClass.hasPublicOrDefaultModifier &&
                    koClass.hasParentInterfaceOf(CopyableComponentState::class)
            }
        }

    @Test
    fun `every feature contract should have @Immutable sealed class Event`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.hasClass { koClass ->
                koClass.hasSealedModifier &&
                    koClass.name == "Event" &&
                    koClass.hasAnnotationOf(Immutable::class) &&
                    koClass.hasPublicOrDefaultModifier
            }
        }

    @Test
    fun `every feature contract should have @Immutable sealed class Effect`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.hasClass { koClass ->
                koClass.hasSealedModifier &&
                    koClass.name == "Effect" &&
                    koClass.hasAnnotationOf(Immutable::class) &&
                    koClass.hasPublicOrDefaultModifier
            }
        }

    @Test
    fun `every feature contract name should end with Contract postfix`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.hasNameEndingWith("Contract")
        }

    @Test
    fun `every feature contract should be placed inside contract package`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.resideInPackage("dev.kigya.mindplex.feature..contract")
        }

    @Test
    fun `every feature contract should have public access modifier`() =
        contracts.assertTrue { koClassContract ->
            koClassContract.hasPublicOrDefaultModifier
        }
}
