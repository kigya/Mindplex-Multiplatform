plugins {
    `kotlin-dsl`
}

dependencies {
    api(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
