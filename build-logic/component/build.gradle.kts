plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.android)
    implementation(libs.gradle.ksp)
    implementation(libs.gradle.ktorfit)

    implementation(projects.buildFeature)
    implementation(projects.gradleExt)
}
