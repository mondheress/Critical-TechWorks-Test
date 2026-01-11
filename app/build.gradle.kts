import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    namespace = "com.criticaltechwork.bbcnews"

    compileSdk = 36
    flavorDimensions += "content"

    productFlavors {
        create("bbc") {
            dimension = "content"
            applicationIdSuffix = ".bbc"
            isDefault = true
            buildConfigField("String", "NEWS_SOURCE_ID", "\"bbc-news\"")
            resValue("string", "app_name", "BBC News")
            resValue("string", "news_provider_title", "BBC News")
        }

        create("abc") {
            dimension = "content"
            applicationIdSuffix = ".abc"
            buildConfigField("String", "NEWS_SOURCE_ID", "\"abc-news\"")
            resValue("string", "app_name", "ABC News")
            resValue("string", "news_provider_title", "ABC News")
        }
    }

    defaultConfig {
        applicationId = "com.criticaltechwork.bbcnews"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = localProperties.getProperty("NEWS_API_KEY") ?: ""
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        dataBinding = false
        viewBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.glide)
    kapt(libs.hilt.compiler)
    implementation(libs.intuit.ssp)
    implementation(libs.intuit.sdp)
    implementation(libs.androidx.biometric)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}