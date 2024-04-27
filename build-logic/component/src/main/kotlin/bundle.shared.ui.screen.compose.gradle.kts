import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("component.shared.compose")
}

configure<KotlinMultiplatformExtension> {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kotlinx.collections.immutable)
            }
            with(compose.dependencies) {
                implementation(components.resources)
                implementation(animation)
            }
        }
        androidMain.dependencies {
            with(compose.dependencies) {
                implementation(preview)
                implementation(uiTooling)
            }
        }
    }
}
