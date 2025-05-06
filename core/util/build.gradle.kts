import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension

plugins {
    with(libs.plugins.convention) {
        alias(config.shared.library)
        alias(bundle.shared.ui.screen.compose)
        alias(component.koin)
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            with(libs) {
                implementation(coroutines.core)
                implementation(compose.navigation)
                api(compose.shimmer)
                with(dataStore) {
                    implementation(core)
                    implementation(preferences)
                }
            }
        }
    }
}

plugins.withId("org.jetbrains.kotlin.multiplatform") {
    apply(plugin = "com.codingfeline.buildkonfig")

    val buildType = when {
        project.hasProperty("release") -> "release"
        System.getenv("CONFIGURATION") == "Release" -> "release"
        else -> "debug"
    }

    extensions.configure<BuildKonfigExtension> {
        packageName = "dev.kigya.mindplex.build"
        defaultConfigs {
            buildConfigField(FieldSpec.Type.STRING, "BUILD_TYPE", buildType)
        }
    }
}
