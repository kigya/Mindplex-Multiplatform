import com.android.build.gradle.BaseExtension
import dev.kigya.mindplex.config.libs

plugins {
    kotlin("android")
    id("internal.config.shared.android")
}

configure<BaseExtension> {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kotlin {
        jvmToolchain(project.libs.versions.java.get().toInt())
    }
}
