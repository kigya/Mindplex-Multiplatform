plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.kotlin.multiplatform)
    implementation(libs.gradle.android)

    implementation(projects.gradleExt)
}
