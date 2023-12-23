plugins {
    alias(libs.plugins.nowinandroid.android.library)
//    alias(libs.plugins.nowinandroid.android.library.jacoco)
    alias(libs.plugins.nowinandroid.android.hilt)
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
//    implementation(projects.core.analytics)
    implementation(projects.core.common)
//    implementation(projects.core.database)
//    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(projects.core.network)
//    implementation(projects.core.notifications)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

//    testImplementation(projects.core.datastoreTest)
    testImplementation(projects.core.testing)
}