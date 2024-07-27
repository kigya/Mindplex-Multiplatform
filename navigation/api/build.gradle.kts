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
            }
        }
    }
}
