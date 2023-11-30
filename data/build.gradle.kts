plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val retrofit2Version = "2.9.0"
val okhttp3Version = "4.9.1"

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(path = ":domain"))


    implementation(libs.bundles.retrofit2)
    implementation(libs.bundles.okhttp3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}