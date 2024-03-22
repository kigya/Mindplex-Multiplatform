plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            implementation(kotlin)
            implementation(ksp)
            implementation(android)
            implementation(ktorfit)
        }
    }

    with(projects) {
        implementation(buildFeature)
        implementation(gradleExt)
    }
}
