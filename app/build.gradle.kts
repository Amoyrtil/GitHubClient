import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.power.assert)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.gradle.ktlint)
}

android {
    namespace = "com.kaeritei.githubclient"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kaeritei.githubclient"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = System.getenv("BUILD_VERSION")?.toInt() ?: 1
        versionName = System.getenv("APP_VERSION") ?: "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
        buildConfig = true
    }
    ktlint {
        android = true
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    powerAssert {
        // デバッグ情報を表示させたい関数・メソッド
        functions =
            listOf(
                "kotlin.assert",
                "kotlin.require",
                "kotlin.test.assertTrue",
                "kotlin.test.assertEquals",
                "kotlin.test.assertNull",
            )
        // デバッグ情報を表示させたいソースセット
        includedSourceSets = listOf("debugUnitTest")
    }
}

dependencies {
    implementation(project(":api"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.browser)
    implementation(libs.kotlinx.serialization.core)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    lintChecks(libs.compose.lint.checks)
}
