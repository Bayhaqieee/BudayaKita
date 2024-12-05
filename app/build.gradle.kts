plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")

}

android {
    namespace = "com.example.budayakita"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.budayakita"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    implementation (libs.androidx.activity.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.firestore.ktx)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)

    implementation (libs.androidx.viewpager2)

    implementation(libs.androidx.constraintlayout)

    // Lifecycle dan ViewModel untuk MVVM
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation Component untuk navigasi antar layar
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Retrofit untuk komunikasi API
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // OkHttp untuk logging API
    implementation (libs.logging.interceptor)

    // Glide untuk memuat gambar
    implementation (libs.glide)
    kapt (libs.compiler)

    // Coroutine untuk pengelolaan asinkron
    implementation (libs.kotlinx.coroutines.android)


    // Testing tambahan untuk pengujian unit dan UI
    testImplementation (libs.androidx.core)
    testImplementation (libs.kotlinx.coroutines.test)

    implementation ("androidx.core:core:1.9.0")
    implementation ("androidx.datastore:datastore-preferences:1.1.1")
    

}
