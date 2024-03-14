plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.reviro.weather"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.reviro.weather"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.appVersionCode
        versionName = AppConfig.appVersionName

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(project(":common"))
    api(project(":domain"))
    api(project(":data"))

    addUiDependencies()
    addAndroidTestsDependencies()
    addCoroutinesDependencies()
    addAndroidLifecycleDependencies()
    addLocationDependency()
    addNetworkDependencies()
    addNavigationDependencies()
    addHiltDependencies()

}