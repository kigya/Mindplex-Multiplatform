import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("component.shared.compose")
    id("build-feature.compose")
}

configure<KotlinMultiplatformExtension> {
    sourceSets {
        with(compose.dependencies) {
            commonMain.dependencies {
                implementation(components.resources)
            }
            androidMain.dependencies {
                implementation(preview)
                implementation(uiTooling)
            }
        }
    }
}
