plugins {
    alias(libs.plugins.nowinandroid.android.library)
//    alias(libs.plugins.nowinandroid.android.library.jacoco)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.linversion.speedy.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    ksp(libs.hilt.compiler)

    testImplementation(projects.core.testing)
}