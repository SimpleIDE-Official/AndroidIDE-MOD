plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.termux.emulator"
    ndkVersion = BuildConfig.ndkVersion

    defaultConfig {
        externalNativeBuild {
            ndkBuild {
                cFlags += arrayOf("-std=c11", "-Wall", "-Wextra", "-Werror", "-Os", "-fno-stack-protector", "-Wl,--gc-sections")
            }
        }

        ndk {
            abiFilters += arrayOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
        }
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

tasks.withType(Test::class.java) {
    testLogging {
        events("started", "passed", "skipped", "failed")
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    testImplementation(projects.testing.unit)
}
