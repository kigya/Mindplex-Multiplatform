import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

configure<KotlinMultiplatformExtension> {
    sourceSets.commonMain {
        dependencies {
            implementation(libs.koin.core)
        }
    }
}
