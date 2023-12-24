plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
//    alias(libs.plugins.nowinandroid.android.library.jacoco)
}

android {
    namespace = "com.linversion.speedy.home"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
}