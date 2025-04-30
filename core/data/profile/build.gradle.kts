plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(serialization)
            alias(room)
            alias(ktor)
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(bundles.dataStore)
            }
            with(projects) {
                implementation(core.domain.profile)
                implementation(core.data.firebase)
                implementation(core.util)
                implementation(core.data.scout)
            }
        }
    }
}
