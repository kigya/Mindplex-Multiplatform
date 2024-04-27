import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("plugin.serialization")
}

configure<KotlinMultiplatformExtension> {
    sourceSets.commonMain {
        dependencies {
            with(libs) {
                implementation(bundles.decompose)
                implementation(essenty)
            }
        }
    }
}
