// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    //new
    // Keep the kotlin-android plugin if your project uses Kotlin alongside Java, otherwise remove it
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false // Add this line
}
