plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(component.unit.test)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(annotation)
            }
            with(projects) {
                api(core.domain.interactor)
                api(core.domain.connectivity)
                implementation(core.util)
            }
        }
    }
}
