import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.gradle.ktlint)
}

android {
    namespace = "com.kaeritei.api"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        localProperties.load(rootProject.file("local.properties").inputStream())
        val personalAccessToken = localProperties.getProperty("PERSONAL_ACCESS_TOKEN")
        buildConfigField("String", "PERSONAL_ACCESS_TOKEN", "\"$personalAccessToken\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvm.get()
    }
    buildFeatures {
        buildConfig = true
    }
    ktlint {
        android = true
    }
}

dependencies {
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.moshi.kotlin)
}
