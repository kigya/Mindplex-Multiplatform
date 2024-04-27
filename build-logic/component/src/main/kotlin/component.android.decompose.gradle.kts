plugins {
    kotlin("plugin.serialization")
}

dependencies {
    withLibsVersionCatalog { libs ->
        implementation(libs.decompose)
    }
}
