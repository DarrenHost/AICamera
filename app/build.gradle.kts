plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.coffeeji.aicamera"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.coffeeji.aicamera"
        minSdk = 30
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    androidResources {
        // .task (MediaPipe) 与 .tflite 必须保持未压缩，才能从 assets 直接内存映射/读取
        noCompress += listOf("tflite", "task")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // CameraX: USB UVC camera preview + frame analysis on dev board
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    // MediaPipe Tasks Vision: face detection
    implementation(libs.mediapipe.tasks.vision)

    // LiteRT (TensorFlow Lite 2.17+ 运行时): 本地离线属性推理
    // 旧 org.tensorflow:tensorflow-lite:2.16.1 不支持模型的 FULLY_CONNECTED v12；
    // LiteRT 保持 org.tensorflow.lite.* 包名，业务代码无需改动
    implementation(libs.litert)
    implementation(libs.litert.gpu)
    // GpuDelegate 字节码依赖 GpuDelegateFactory$Options，gpu 模块 POM 未传递声明，需显式引入
    implementation(libs.litert.gpu.api)
    // 提供 FileUtil / TensorBuffer，并传递依赖 litert
    implementation(libs.litert.support)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}