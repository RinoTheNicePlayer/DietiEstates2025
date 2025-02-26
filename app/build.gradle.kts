import com.android.build.api.variant.BuildConfigField
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val localProp = Properties()
val file = file("local.properties")
if (file.exists() && file.isFile){
    file.inputStream().use { localProp.load(it) }
}

android {
    namespace = "com.example.dietiestates25"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dietiestates25"
        minSdk = 31
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
            BuildConfigField("String", "MAPS_API_KEY", localProp.getProperty("MAPS_API_KEY"))
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        buildConfig = true
        viewBinding = true
    }
}

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(libs.mockito.core)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.coil)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.places)
    implementation(libs.playServicesMaps)
    implementation(libs.aws.storage.s3)
    implementation(libs.aws.auth.cognito)
    implementation(libs.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation (libs.okhttp)
    implementation (libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}