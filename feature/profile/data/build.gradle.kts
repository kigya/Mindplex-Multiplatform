plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        with(component) {
            alias(unit.test)
            alias(room)
            alias(serialization)
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
                implementation(feature.profile.domain)
                with(core) {
                    implementation(data.profile)
                    implementation(domain.profile)
                    implementation(util)
                    implementation(data.firebase)
                }
            }
        }
    }
}
