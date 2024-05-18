import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("internal.config.android")
    id("internal.config.shared.detekt")
}

configure<KotlinMultiplatformExtension> {
    androidTarget {
        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(libs.versions.java.get()))
            }
        }
    }

    arrayOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            binaryOption("bundleId", "com.kigya.mindplex.shared")
            isStatic = true
        }
    }
}

/**
 * This task is designed to copy Compose resources from a core presentation module to
 * a shared module, ensuring that resources required for the Compose UI are available across
 * different modules, particularly for iOS builds in a Kotlin Multiplatform environment.
 */
tasks.register("copyComposeResources", Copy::class) {
    val sourceDir = "${rootProject.projectDir}/core/presentation/resources/src/commonMain/composeResources"
    val destinationDir = "${rootProject.projectDir}/shared/src/commonMain/composeResources"

    from(sourceDir)
    into(destinationDir)
}

/**
 * Task responsible for clearing out all resources from the shared module's Compose resource directory.
 * This is crucial for avoiding issues related to stale or conflicting resources during the build process,
 * especially before starting a new build after executing *copyComposeResources*.
 */
tasks.register("clearComposeResources", Delete::class) {
    val destinationDir = "${rootProject.projectDir}/shared/src/commonMain/composeResources"
    delete(destinationDir)
}

project.afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("clearComposeResources")
    }
    tasks.named("linkDebugFrameworkIosArm64") {
        dependsOn("clearComposeResources")
        dependsOn("copyComposeResources")
    }
    tasks.named("linkDebugFrameworkIosSimulatorArm64") {
        dependsOn("clearComposeResources")
        dependsOn("copyComposeResources")
    }
}

tasks.register("testClasses")
