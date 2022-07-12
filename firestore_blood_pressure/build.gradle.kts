buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.kotlinSerialization)
        classpath(Build.hiltAndroid)
        classpath(Build.androidLibrary)
        classpath(Build.gmsGoogle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

