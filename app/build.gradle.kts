plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.happidapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.happidapplication"
        minSdk = 24
        targetSdk = 34
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
    dataBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.pinview)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.rxjava2.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.dexter)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("org.jetbrains:annotations:15.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("pub.devrel:easypermissions:3.0.0")
    implementation("com.github.niqo01.rxplayservices:rx-play-services-location:0.4.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.squareup.okio:okio:2.2.2")
    implementation ("com.squareup.okhttp3:okhttp:3.14.1")




}