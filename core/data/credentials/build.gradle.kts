plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.serialization)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(firebase.firestore)
                implementation(firebase.common)
                implementation(coroutines.core)
            }
            with(projects) {
                implementation(core.data.firebase)
                implementation(core.util)
            }
        }
    }
}
