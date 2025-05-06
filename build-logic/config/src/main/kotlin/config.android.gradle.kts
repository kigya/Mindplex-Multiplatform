import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import extension.configureIfExists
import extension.getInt
import extension.libs
import java.util.Properties

plugins {
    kotlin("android")
    id("internal.config.android")
    id("internal.config.kotlin.detekt")
}

configure<BaseExtension> {
    val localProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties["release.store.file"] as String)
            storePassword = localProperties["release.store.password"] as String
            keyAlias = localProperties["release.key.alias"] as String
            keyPassword = localProperties["release.key.password"] as String
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
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
