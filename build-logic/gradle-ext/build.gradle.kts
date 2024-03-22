plugins {
    `kotlin-dsl`
}

dependencies {
    with(libs) {
        with(gradle) {
            api(compose)
        }

        api(files(javaClass.superclass.protectionDomain.codeSource.location))
    }
}
