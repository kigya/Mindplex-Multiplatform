plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.serialization)
        alias(component.unit.test)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(bundles.dataStore)
                implementation(firebase.firestore)
                implementation(firebase.common)
            }
            with(projects) {
                implementation(feature.login.domain)
                implementation(core.util)
                implementation(core.data.firebase)
                implementation(core.data.jwtParser)
            }
        }
    }
}
