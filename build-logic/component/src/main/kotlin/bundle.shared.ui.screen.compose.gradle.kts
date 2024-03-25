import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("component.shared.compose")
}

configure<KotlinMultiplatformExtension> {
    sourceSets.commonMain.dependencies {
        with(compose.dependencies) {
            implementation(components.resources)
            implementation(preview)
        }
    }
}
