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
                implementation(coroutines.core)
                implementation(firebase.firestore)
                implementation(firebase.common)
            }
            with(projects) {
                implementation(core.domain.profile)
                implementation(core.data.firebase)
                implementation(core.util)
            }
        }
    }
}
