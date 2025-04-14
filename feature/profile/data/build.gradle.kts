plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.unit.test)
        with(component) {
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
                implementation(kotlinx.datetime)
                implementation(firebase.firestore)
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
