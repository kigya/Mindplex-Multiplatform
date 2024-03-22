import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension

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
        jvmToolchain(project.libs.versions.java.getInt())
    }
}

configureIfExists(CommonExtension::class.java) {
    lint {
        htmlReport = false
        baseline = file("${project.projectDir}/lint-baseline.xml")
    }
}
