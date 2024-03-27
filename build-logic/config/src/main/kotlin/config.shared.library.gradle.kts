import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("internal.config.shared.android")
}

configure<KotlinMultiplatformExtension> {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
            }
        }
    }

    arrayOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "umbrella"
            binaryOption("bundleId", "com.kigya.mindplex.umbrella")
            isStatic = true
            //export(project(":shared:core:presentation:theme"))
        }
    }
}

tasks.register("testClasses")
