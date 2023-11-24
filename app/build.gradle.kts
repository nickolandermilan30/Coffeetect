plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.coffeetech"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.coffeetech"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        mlModelBinding = true
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.0")


    implementation ("androidx.camera:camera-core:1.2.2")
    implementation ("androidx.camera:camera-camera2:1.2.2")
    implementation ("androidx.camera:camera-lifecycle:1.2.2")
    implementation ("androidx.camera:camera-view:1.2.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("androidx.annotation:annotation:1.4.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")

    implementation ("androidx.camera:camera-core:1.0.0")
    implementation ("androidx.camera:camera-camera2:1.0.0")
    implementation ("androidx.camera:camera-lifecycle:1.0.0")
    implementation ("androidx.camera:camera-view:1.0.0")

// Marker
    implementation ("androidx.camera:camera-core:1.1.0-alpha01")
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha01")
    implementation ("androidx.camera:camera-lifecycle:1.1.0-alpha01")
    implementation ("androidx.camera:camera-view:1.1.0-alpha06")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.firebase:firebase-inappmessaging:20.4.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    implementation("androidx.test:monitor:1.6.1")
    implementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("junit:junit:4.12")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("androidx.camera:camera-core:1.2.2")
    implementation ("androidx.camera:camera-camera2:1.2.2")
    implementation ("androidx.camera:camera-lifecycle:1.2.2")
    implementation ("androidx.camera:camera-view:1.2.2")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.1.0")

    implementation ("androidx.camera:camera-view:1.1.0-alpha01")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.google.android.material:material:1.3.0-alpha01")
    implementation ("androidx.camera:camera-camera2:1.0.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation ("org.tensorflow:tensorflow-lite:2.5.0")

    //Template
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    testImplementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

}