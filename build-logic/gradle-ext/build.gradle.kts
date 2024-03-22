plugins {
    `kotlin-dsl`
}

dependencies {
    api(libs.gradle.compose)

    api(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
