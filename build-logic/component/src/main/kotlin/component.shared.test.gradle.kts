import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

configure<KotlinMultiplatformExtension> {
    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
