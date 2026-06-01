plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.hophey.pract2"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.hophey.pract2"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // ViewModel dependency
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.10.0")

    // Navigation dependency
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // JSON serialization dependency
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    // Koin
    implementation("io.insert-koin:koin-android:4.2.1")
    implementation("io.insert-koin:koin-androidx-compose:4.2.1")

    // Koin
    implementation("io.ktor:ktor-client-core:3.5.0")
    implementation("io.ktor:ktor-client-android:3.5.0")
    implementation("io.ktor:ktor-client-logging:3.5.0")
    implementation("io.ktor:ktor-client-auth:3.5.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.5.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.5.0")

    // Coil
    implementation("io.coil-kt.coil3:coil-compose:3.4.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.4.0")

    // Icons
    implementation("androidx.compose.material:material-icons-core")
}