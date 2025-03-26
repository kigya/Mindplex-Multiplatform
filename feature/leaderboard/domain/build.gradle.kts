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
            }
            with(projects) {
                api(core.domain.interactor)
                implementation(core.util)
                implementation(core.domain.connectivity)
            }
        }
    }
}
