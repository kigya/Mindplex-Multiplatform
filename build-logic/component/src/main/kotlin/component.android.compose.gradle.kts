plugins {
    id("build-feature.compose")
}

dependencies {
    withLibsVersionCatalog { libs ->
        implementation(platform(libs.compose.android.bom))
        implementation(libs.compose.android.ui)
        implementation(libs.compose.android.ui.graphics)
        implementation(libs.compose.android.ui.tooling.preview)
        implementation(libs.compose.android.material3)
    }
}
