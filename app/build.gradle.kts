plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gestionpeliculasseries"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gestionpeliculasseries"
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
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0") // Opcional, para ver los logs de las peticiones
    implementation ("com.github.bumptech.glide:glide:4.12.0")         // Opcional, si planeas cargar imágenes con Glide
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")  // Opcional, para Glide
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    }