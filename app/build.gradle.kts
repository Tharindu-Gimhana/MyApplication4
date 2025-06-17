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

    //newly added Glide dependencies
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:20.3.1") // Check for the latest version

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2") // Check for the latest version

    // CardView
    implementation ("androidx.cardview:cardview:1.0.0") // Check for the latest version

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.16.0") // Check for the latest version
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // If you haven't explicitly added it, ConstraintLayout
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4") // Check for the latest version

    // Required for EdgeToEdge in AppCompatActivity
    implementation ("androidx.activity:activity:1.9.0")
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")// Or a newer version if av



}