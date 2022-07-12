
plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$project.rootDir/proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"

        @Suppress("SuspiciousCollectionReassignment")
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }

    kapt {
        // Allow references to generated code
        correctErrorTypes = true
        useBuildCache = true
    }
}

dependencies {

    // Kotlin
    implementation(KotlinX.Coroutines.core)
    implementation(KotlinX.Coroutines.android)
    implementation(KotlinX.Json.json)

    // Lifecycle
    implementation(AndroidX.Lifecycle.livedata)
    implementation(AndroidX.Lifecycle.viewmodel)

    // Fragment
    implementation(AndroidX.Fragment.fragment)

    // Activity
    implementation(AndroidX.Activity.activity)

    // Core
    implementation(AndroidX.Core.core)

    // Appcompat
    implementation(AndroidX.Appcompat.appcompat)

    // Navigation
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    //Google
    implementation(Google.Material.material)
    implementation(Google.Firebase.firestore)
    implementation(Google.Firebase.auth)
    implementation(Google.Firebase.authKtx)
    implementation(Google.Firebase.uiAuth)

    // ConstraintLayout
    implementation(AndroidX.ConstraintLayout.constraintLayout)

    // Hilt
    implementation(HiltDagger.android)
    kapt(HiltDagger.compiler)

    testImplementation(Test.Junit.jupiter)
    testImplementation(Test.Mockito.mockitoinline)
    testImplementation(Test.Mockito.mockitoKotlinMockito)
    testImplementation(Test.Androidx.arch)
    testImplementation(Test.Coroutines.test)
    testImplementation(Test.Junit.junit)
    testImplementation(Test.Mockito.mockitoMockito)

}