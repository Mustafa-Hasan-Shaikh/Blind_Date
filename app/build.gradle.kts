plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.dating.blinddate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dating.blinddate"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //For Card Stack
    implementation ("com.github.yuyakaido:CardStackView:v2.3.4")
    // For Gif ImageView
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    /*Firbase Dependecy */
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    
    /*Picasso for Image Rendering */
    implementation ("com.squareup.picasso:picasso:2.8")
    /* For getting Circular ImageView */
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // For converting Object data jo Json (String Formate)
    implementation("com.google.code.gson:gson:2.8.8")

    /* For Retrofit Library */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    /* For Flow Layout */
    implementation ("com.nex3z:flow-layout:1.3.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}