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

    compileSdkVersion(libs.versions.compile.sdk.getInt())

    defaultConfig {
        minSdk = libs.versions.min.sdk.getInt()
        targetSdk = libs.versions.target.sdk.getInt()

        resourceConfigurations += listOf("ru", "en")

        testOptions.unitTests.apply {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    compileOptions {
        sourceCompatibility(project.libs.versions.java.get())
        targetCompatibility(project.libs.versions.java.get())
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/{AL2.0,LGPL2.1}"
            )
        )
    }
}

configureIfExists(CommonExtension::class.java) {
    lint {
        htmlReport = false
        baseline = file("${project.projectDir}/lint-baseline.xml")
    }
}
