plugins {
    alias(libs.plugins.nowinandroid.android.library)
//    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.nowinandroid.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.linversion.speedy.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.common)
    implementation(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.model)
    api(projects.core.network)
//    implementation(projects.core.analytics)
//    implementation(projects.core.notifications)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.tracing.ktx)
    testImplementation(projects.core.datastoreTest)
    testImplementation(projects.core.testing)
}