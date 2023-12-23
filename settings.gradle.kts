import java.net.URI

include(":core:designsystem")


include(":core:model")


include(":core:domain")


include(":core:data")


pluginManagement {
    includeBuild("build-logic")

    repositories {
        maven {
            url = java.net.URI.create("https://maven.aliyun.com/repository/public/")
        }
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/google/")}
        maven { url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin/")}
        maven { url = java.net.URI.create("https://jitpack.io") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = java.net.URI.create("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = java.net.URI.create("https://maven.aliyun.com/repository/google/")
        }
        maven {
            url = java.net.URI.create("https://maven.aliyun.com/repository/gradle-plugin/")
        }
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Speedy"
include(":app")
include(":benchmarks")
include(":core:common")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":core:designsystem")
include(":core:testing")
include(":LocalRepo:shimmer")
 