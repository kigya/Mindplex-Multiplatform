plugins {
    id("com.google.devtools.ksp")
    id("de.jensklingenberg.ktorfit")
}

dependencies {
    with(libs) {
        ksp(ktorfit.ksp)
        implementation(ktorfit)
    }
}
