import dev.kigya.mindplex.config.withLibsVersionCatalog

plugins {
    id("com.google.devtools.ksp")
    id("de.jensklingenberg.ktorfit")
}

dependencies {
    withLibsVersionCatalog { libs ->
        ksp(libs.ktorfit.ksp)
        implementation(libs.ktorfit)
    }
}
