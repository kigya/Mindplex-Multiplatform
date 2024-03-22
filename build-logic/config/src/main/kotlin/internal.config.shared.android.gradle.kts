import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension

/**
 * Before using this plugin, ensure that necessary Android configurations have been applied.
 * Note: This script does not configure the Kotlin JVM version.
 */

configure<BaseExtension> {
    val projectNameFormatted = project.path.drop(1).replace(Regex("[-:]"), ".")
    println("Namespace: ${project.path} -> $projectNameFormatted")
    namespace = "dev.kigya.mindplex.$projectNameFormatted"

    compileSdkVersion(libs.versions.compileSdk.getInt())

    defaultConfig {
        minSdk = libs.versions.minSdk.getInt()
        targetSdk = libs.versions.targetSdk.getInt()

        resourceConfigurations += listOf("ru", "en")
    }

    compileOptions {
        sourceCompatibility(project.libs.versions.java.get())
        targetCompatibility(project.libs.versions.java.get())
    }

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

configureIfExists(CommonExtension::class.java) {
    lint {
        htmlReport = false
        baseline = file("${project.projectDir}/lint-baseline.xml")
    }
}
