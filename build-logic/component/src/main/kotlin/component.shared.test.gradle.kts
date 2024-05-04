import gradle.kotlin.dsl.accessors._2b40ae1520a174b163f8f8ab716be797.compose
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

@OptIn(ExperimentalComposeLibrary::class)
configure<KotlinMultiplatformExtension> {
    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(compose.dependencies.uiTest)
            with(libs) {
                implementation(coroutines.test)
                implementation(turbine)
            }
        }
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            instrumentedTestVariant {
                sourceSetTree.set(KotlinSourceSetTree.test)
                dependencies {
                    with(libs) {
                        with(compose.ui.test) {
                            implementation(junit4)
                            debugImplementation(manifest)
                        }
                        implementation(mockk)
                    }
                }
            }
        }
    }
}
