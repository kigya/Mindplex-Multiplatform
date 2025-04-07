plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.ktor)
        alias(component.serialization)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
            }
            with(projects) {
                implementation(core.util)
            }
        }
    }
}
