import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.infinity.interactive.scanqr.generateqr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.infinity.interactive.scanqr.generateqr"
        minSdk = 24
        targetSdk = 34
        versionCode = 6
        versionName = "1.0.6"

        val date =  Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy") // Specify your desired date format
        val formattedDate = dateFormat.format(date)
        setProperty("archivesBaseName", "QR_Code_Scanner" + "_VN_" +
                versionName + "_VC_" + versionCode + "_ Time_ "+ formattedDate)

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

//        isCoreLibraryDesugaringEnabled  = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }

    bundle {
        language {
            enableSplit = false
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Lifecycle
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    annotationProcessor(libs.androidx.lifecycle.compiler)

    //Navigation Library
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Eyes Dots QR
    implementation(libs.custom.qr.generator)

    // Firebase BOM
    implementation(platform(libs.firebase.bom))

    // Firebase libraries
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)

    //Third-party-libraries
    implementation(libs.dotsindicator)
    implementation(libs.sdp)
    implementation(libs.glide)
    implementation(libs.zxing.android.embedded)
    implementation(libs.zxing.core)
    implementation(libs.google.play.services.maps)
    implementation(libs.google.play.services.location)
    implementation(libs.dexter)
    implementation(libs.indicatorseekbar)
//    implementation(libs.simplecropview)
    implementation(libs.material.spinner)
    implementation(libs.itextpdf)

}

