import extension.implementation
import extension.ksp
import extension.libs

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
