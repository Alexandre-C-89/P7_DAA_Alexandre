plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.p7_daa_alexandre"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.p7_daa_alexandre"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.0-alpha01")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.square.retrofit2:retrofit:2.5.0")
    implementation ("com.square.retrofit2:converter-gson:2.5.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.squareup.okio:okio:2.2.2")
    implementation ("com.squareup.okhttp3:okhttp:3.14.9")
    implementation ("android.arch.lifecycle:view-model:1.1.1")
    implementation ("android.arch.lifecycle:extensions:1.1.1")
    androidTestImplementation ("androidx.test:runner:1.6.0-alpha04")


    // FIREBASE
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    // FIREBASE UI
    implementation ("com.firebase:firebase-ui-auth:6.4.0")

    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")

    //VIEW MODEL & LIVE DATA
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //ROOM
    implementation ("androidx.room:room-runtime:2.6.0")
    annotationProcessor ("androidx.room:room-compiler:2.6.0")

}