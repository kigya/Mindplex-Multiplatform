import dev.kigya.mindplex.config.withLibsVersionCatalog

plugins {
    id("component.android.compose")
}

dependencies {
    withLibsVersionCatalog { libs ->
        debugImplementation(libs.compose.android.ui.tooling)
        debugImplementation(libs.compose.android.ui.test.manifest)
    }
}
