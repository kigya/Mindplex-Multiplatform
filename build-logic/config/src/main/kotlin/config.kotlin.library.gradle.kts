import dev.kigya.mindplex.config.libs

plugins {
    `java-library`
    kotlin("jvm")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.java.get())
}
