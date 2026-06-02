//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.jetbrains.kotlin.android)
//}
//
//android {
//    namespace = "com.ele.telecallerapp"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.ele.telecallerapp"
//        minSdk = 29
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables { useSupportLibrary = true }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    kotlinOptions { jvmTarget = "1.8" }
//
//    buildFeatures { compose = true }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.14"
//    }
//
//    packaging {
//        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
//    }
//}
//
//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//
//    implementation(libs.androidx.navigation.compose)
//    implementation("androidx.compose.material:material-icons-extended")
//
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
//    implementation("io.coil-kt:coil-compose:2.5.0")
//
//    implementation("io.coil-kt:coil-compose:2.6.0")
//    implementation("androidx.activity:activity-compose:1.9.2")
//    implementation("androidx.compose.material3:material3:1.2.1")
//
//    // ---------------- CORE ----------------
//    implementation ("androidx.core:core-ktx:1.12.0")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
//    implementation ("androidx.activity:activity-compose:1.9.0")
//
//    // ---------------- COMPOSE ----------------
//
//    implementation ("androidx.compose.ui:ui")
//    implementation ("androidx.compose.material3:material3")
//    implementation ("androidx.navigation:navigation-compose:2.7.7")
//
//    // ---------------- RETROFIT ----------------
//    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
//    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
//
//    // ---------------- ROOM (FIXES YOUR ERRORS) ----------------
//    implementation ("androidx.room:room-runtime:2.6.1")
//    implementation ("androidx.room:room-ktx:2.6.1")
//
//
//    // ---------------- COROUTINES ----------------
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
//
//    // ---------------- FIREBASE ----------------
//
//    implementation ("com.google.firebase:firebase-messaging")
//}


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}



android {
    namespace = "com.ele.telecallerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ele.telecallerapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
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

    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures { compose = true }

    buildFeatures {
        buildConfig =true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}


//
//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//
//    implementation(libs.androidx.navigation.compose)
//    implementation("androidx.compose.material:material-icons-extended")
//
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
//    implementation("io.coil-kt:coil-compose:2.5.0")
//
//    implementation("io.coil-kt:coil-compose:2.6.0")
//    implementation("androidx.activity:activity-compose:1.9.2")
//    implementation("androidx.compose.material3:material3:1.2.1")
//
//    /* ---------------- COMPOSE ---------------- */
//    implementation("androidx.compose.ui:ui:1.6.7")
//    implementation("androidx.compose.material3:material3:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.6.7")
//    debugImplementation("androidx.compose.ui:ui-tooling:1.6.7")
//
//    implementation("androidx.activity:activity-compose:1.9.0")
//    implementation("androidx.navigation:navigation-compose:2.7.7")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
//
//    /* ---------------- ROOM ---------------- */
//    implementation("androidx.room:room-runtime:2.6.1")
//    implementation("androidx.room:room-ktx:2.6.1")
//    kapt("androidx.room:room-compiler:2.6.1")
//
//    /* ---------------- RETROFIT ---------------- */
//    implementation("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
//
//    /* ---------------- COROUTINES ---------------- */
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
//
//    /* ---------------- FIREBASE ---------------- */
//    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.firebase:firebase-messaging-ktx")
//    implementation("androidx.compose.ui:ui-text")
//    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
//}
//
//
//
//



dependencies {

    /* ---------------- CORE ---------------- */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    /* ---------------- COMPOSE (ONLY ONE SYSTEM - BOM) ---------------- */
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    implementation("androidx.compose.material:material-icons-extended")

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* ---------------- NAVIGATION + MVVM ---------------- */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    /* ---------------- NETWORK ---------------- */
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    /* ---------------- IMAGE ---------------- */
    implementation("io.coil-kt:coil-compose:2.6.0")

    /* ---------------- COROUTINES ---------------- */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    /* ---------------- ROOM ---------------- */
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    /* ---------------- FIREBASE ---------------- */
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    /* ---------------- TEST ---------------- */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}