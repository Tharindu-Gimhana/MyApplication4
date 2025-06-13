plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Add this for Firebase
}

android {
    namespace = "com.example.fotnewscorner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fotnewscorner"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.recyclerview)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Import the Firebase BoM (Bill of Materials)
    implementation(platform(libs.firebase.bom))

// Firebase Authentication
    implementation(libs.firebase.auth.ktx)

// Cloud Firestore
    implementation(libs.firebase.firestore.ktx)

    // Room components
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler) // Use 'annotationProcessor' for Java projects
// Kotlin Extensions and Coroutines support for Room (only needed if using Kotlin extensions in Java, otherwise can be omitted)
    implementation(libs.room.ktx)


}