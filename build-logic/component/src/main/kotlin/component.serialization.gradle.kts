import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

configure<KotlinMultiplatformExtension> {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(kotlinx.serialization.json)
            }
        }
    }
}
