import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addUiDependencies() {
    uiDependencies.forEach {
        add("implementation", it)
    }
}


fun DependencyHandler.addHiltDependencies() {
    add("implementation", Dependencies.hiltAndroid)
    add("implementation", Dependencies.hiltNavFragment)
    add("kapt", Dependencies.hiltCompiler)
}

fun DependencyHandler.addNetworkDependencies() {
    networkDependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.addTimberDependency() {
    add("implementation", Dependencies.timber)
}

fun DependencyHandler.addCoroutinesDependencies() {
    coroutinesDependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.addAndroidLifecycleDependencies() {
    add("implementation", Dependencies.runtimKtx)
    add("implementation", Dependencies.viewModel)
    add("implementation", Dependencies.viewModelSavedSate)
    add("implementation", Dependencies.liveData)
    add("kapt", Dependencies.lifeCycleCompiler)
}


fun DependencyHandler.addNavigationDependencies() {
    navigationDependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.addSafeArgs() {
    add("classpath", Dependencies.safeArgs)
}


fun DependencyHandler.addAndroidTestsDependencies() {
    add("testImplementation", Dependencies.jUnit)
    add("androidTestImplementation", Dependencies.jUnitExt)
    add("androidTestImplementation", Dependencies.espresso)
}



fun DependencyHandler.addLocationDependency() {
    add("implementation", Dependencies.locationService)
}

fun DependencyHandler.addGlideDependency() {
    add("implementation", Dependencies.glide)
}

fun DependencyHandler.addRoomDependency() {
    add("kapt",Dependencies.roomCompiler)
    add("implementation", Dependencies.roomLibrary)
    add("implementation", Dependencies.roomRuntime)
}

fun DependencyHandler.addShimmerDependency() {
    add("implementation", Dependencies.shimmer)
}
