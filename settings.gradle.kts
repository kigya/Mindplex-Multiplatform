@file:Suppress("UnstableApiUsage")

rootProject.name = "Mindplex-Multiplatform"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-logic")

// Core Modules
include(":core:data:preferences")
include(":core:domain:interactor")
include(":core:presentation:resources")
include(":core:presentation:feature")
include(":core:presentation:component")
include(":core:presentation:theme")
include(":core:util")

// Feature Modules
include(":feature:home:domain")
include(":feature:home:data")
include(":feature:home:presentation")
include(":feature:onboarding:domain")
include(":feature:onboarding:data")
include(":feature:onboarding:presentation")
include(":feature:splash:presentation")

// Navigation Modules
include(":navigation:mediator")
include(":navigation:navigator")

// DI Modules
include(":di:provider")
include(":di:core")

// Application Modules
include(":androidApp")
include(":shared")
