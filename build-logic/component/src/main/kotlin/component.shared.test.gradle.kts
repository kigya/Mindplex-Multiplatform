import gradle.kotlin.dsl.accessors._a38f0d40c97c1df3fbac9066a13273a7.compose
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

configure<KotlinMultiplatformExtension> {
    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))

            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.dependencies.uiTest)
            with(libs) {
                implementation(coroutines.test)
                implementation(turbine)
                implementation(assertk)
            }
        }
    }
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                with(libs) {
                    implementation(core.ktx)
                    implementation(compose.ui.test.junit4.android)
                    debugImplementation(compose.ui.test.manifest)
                }
            }
        }
    }
}

dependencies {
    with(libs) {
        implementation(mockk)
    }
}
