plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
    }
}

kotlin.sourceSets {
    with(libs) {
        commonMain {
            dependencies {
                implementation(coroutines.core)
            }
        }
        androidMain {
            dependencies {
                implementation(lifecycle.runtime.compose)
                implementation(lifecycle.runtime.ktx)
            }
        }
    }

}
