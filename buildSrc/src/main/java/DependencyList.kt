object AppConfig {
    const val minSdk = 24
    const val targetSdk = 34
    const val compileSdk = 34
    const val appVersionCode = 1
    const val appVersionName = "1.0"
}

object Versions {
    const val coreKtxVersion = "1.12.0"
    const val appCompatVersion = "1.4.1"
    const val materialVersion = "1.11.0"
    const val jUnitVersion = "4.13.2"
    const val jUnitExtVersion = "1.1.5"
    const val espressoVersion = "3.5.1"
    const val constraintLayoutVersion = "2.1.4"
    const val fragmentKtxVersion = "1.6.2"
    const val hiltVersion = "2.44"
    const val hiltNavFragmentVersion = "1.0.0"
    const val retrofitVersion = "2.9.0"
    const val okHttpVersion = "4.12.0"
    const val timberVersion = "5.0.1"
    const val lifecycleVersion = "2.6.2"
    const val coroutinesVersion = "1.3.9"
    const val navigationVersion = "2.7.6"
    const val locationVersion = "21.2.0"
    const val chuckerVersion = "3.5.2"
    const val glideVersion = "4.16.0"
    const val gmsVersion = "21.2.0"
    const val roomVersion = "2.5.2"
    const val shimmerVersion = "0.5.0"
}

object Dependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val jUnit = "junit:junit:${Versions.jUnitVersion}"
    const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExtVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"

    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
    const val hiltNavFragment =
        "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavFragmentVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chuckerVersion}"
    const val okkHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    const val runtimKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val viewModelSavedSate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}"
    const val lifeCycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"

    const val navFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navCommonKtx =
        "androidx.navigation:navigation-common-ktx:${Versions.navigationVersion}"
    const val locationService =
        "com.google.android.gms:play-services-location:${Versions.locationVersion}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"

    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomLibrary = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"

    const val safeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"

}
