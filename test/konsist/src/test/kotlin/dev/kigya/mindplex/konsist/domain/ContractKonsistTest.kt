package dev.kigya.mindplex.konsist.domain

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoInterfaceDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

internal class ContractKonsistTest {
    private val contracts = Konsist
        .scopeFromProject()
        .interfaces()
        .filter {
            it.resideInPackage("dev.kigya.mindplex.feature..domain..") && it.hasSealedModifier.not()
        }

    @Test
    fun `every contract should end with Contract`() = contracts.forEach {
        if (!it.name.endsWith("Contract")) {
            fail("Interface does not follow naming convention: ${it.name}")
        }
    }

    @Test
    fun `every contract should be placed inside contract package`() =
        contracts.assertTrue { koInterface ->
            koInterface.resideInPackage("dev.kigya.mindplex.feature..contract")
        }

    @Test
    fun `every contract should be public`() =
        contracts.assertTrue(function = KoInterfaceDeclaration::hasPublicOrDefaultModifier)

    @Test
    fun `every contract should have an implementation in data layer`() =
        contracts.assertTrue { koInterface ->
            koInterface.hasChild { koChild ->
                koChild.resideInPackage("dev.kigya.mindplex.feature..data..")
            }
        }
}
