plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        with(component) {
            alias(koin)
            alias(serialization)
            alias(ktor)
            alias(room)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kotlinx.datetime)
            }
            with(projects) {
                implementation(feature.home.domain)
                implementation(core.util)
                implementation(core.data.credentials)
                implementation(core.data.firebase)
            }
        }
    }
}
