import org.gradle.kotlin.dsl.dependencies

dependencies {
    withLibsVersionCatalog { libs ->
        implementation(libs.konsist)
        implementation(libs.junit.jupiter.engine)
    }
}
