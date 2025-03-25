import org.gradle.kotlin.dsl.dependencies

plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            with(libs) {
                implementation(compose.android.activity)
            }
        }
        commonMain.dependencies {
            with(libs) {
                implementation(compose.navigation)
            }
            with(projects) {
                with(core) {
                    implementation(presentation.theme)
                    implementation(util)
                }
            }
        }
    }
}
