import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import dev.kigya.mindplex.config.configureIfExists
import dev.kigya.mindplex.config.libs

/**
 * Smth with android should be applied before using this plugin.
 * Doesn't configure kotlin jvm version.
 */

configure<BaseExtension> {
    val projectNameFormatted = project.path.drop(1).replace(Regex("[-:]"), ".")
    println("Namespace: ${project.path} -> $projectNameFormatted")
    namespace = "dev.kigya.mindplex.$projectNameFormatted"

    compileSdkVersion(libs.versions.compileSdk.get().toInt())

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
