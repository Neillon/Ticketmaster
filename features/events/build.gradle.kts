import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.neillon.events"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val keystoreFile = project.rootProject.file("gradle.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())
        buildConfigField("String", "API_URL", properties.getProperty("API_URL"))
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))
        multiDexEnabled = true
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        animationsDisabled = true
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.image.coil)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.androidx.multidex)
    implementation(libs.room.runtime)
    implementation(libs.core)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(project(":common"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.mockk)
}