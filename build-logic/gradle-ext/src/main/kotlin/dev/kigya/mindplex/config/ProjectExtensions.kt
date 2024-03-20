package dev.kigya.mindplex.config

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

/**
 * Workaround to make version catalog accessible in convention plugins
 * https://github.com/gradle/gradle/issues/15383
 */
inline fun Project.withLibsVersionCatalog(block: (LibrariesForLibs) -> Unit) {
    if (project.name != "gradle-kotlin-dsl-accessors") {
        val libs = the<LibrariesForLibs>()
        block(libs)
    }
}

inline fun <T : Any> Project.configureIfExists(type: Class<T>, config: T.() -> Unit) {
    extensions.findByType(type)?.apply(config) ?: run {
        println("Type not found $type")
    }
}

inline val Project.libs: LibrariesForLibs
    // TODO must use root project: extension libs does not exist https://github.com/gradle/gradle/issues/18237
    get() = rootProject.extensions.getByName("libs") as LibrariesForLibs
