import java.net.URI



pluginManagement {
    includeBuild("build-logic")

    repositories {
        maven {
            url = java.net.URI.create("https://maven.aliyun.com/repository/public/")
        }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/google/")}
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin/")}
        maven { url = java.net.URI.create("https://jitpack.io") }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Speedy"
include(":core:designsystem")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:data-test")
include(":core:database")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:datastore-test")
include(":core:ui")
include(":core:notifications")
include(":app")
include(":core:screenshot-testing")
include(":benchmarks")
include(":core:common")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":core:designsystem")
include(":core:testing")
include(":LocalRepo:shimmer")
include(":feature:settings")

include(":lint")