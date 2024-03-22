plugins {
    `java-library`
    kotlin("jvm")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.java.getInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.java.getInt())
}
