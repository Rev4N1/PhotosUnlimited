plugins {
    id("com.android.application")
}

android {
    namespace = "com.rev4n.unlimitedphotos"
    compileSdk = 34
    ndkVersion = "27.2.12479018"
    buildToolsVersion = "34.0.0"

    buildFeatures {
        prefab = true
    }

    packaging {
        jniLibs {
            excludes += "**/liblog.so"
            excludes += "**/libdobby.so"
        }
    }

    defaultConfig {
        applicationId = "com.rev4n.unlimitedphotos"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        externalNativeBuild {
            cmake {
                arguments += "-DANDROID_STL=none"
                arguments += "-DCMAKE_BUILD_TYPE=Release"

                cppFlags += "-std=c++20"
                cppFlags += "-fno-exceptions"
                cppFlags += "-fno-rtti"
                cppFlags += "-fvisibility=hidden"
                cppFlags += "-fvisibility-inlines-hidden"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation("org.lsposed.libcxx:libcxx:27.0.12077973")
    implementation("org.lsposed.hiddenapibypass:hiddenapibypass:4.3")
}

tasks.register("copyFiles") {
    doLast {
        val moduleFolder = project.rootDir.resolve("module")
        val dexFile = project.layout.buildDirectory.get().asFile.resolve("intermediates/dex/release/minifyReleaseWithR8/classes.dex")
        val soDir = project.layout.buildDirectory.get().asFile.resolve("intermediates/stripped_native_libs/release/stripReleaseDebugSymbols/out/lib")

        dexFile.copyTo(moduleFolder.resolve("classes.dex"), overwrite = true)

        soDir.walk().filter { it.isFile && it.extension == "so" }.forEach { soFile ->
            val abiFolder = soFile.parentFile.name
            val destination = moduleFolder.resolve("zygisk/$abiFolder.so")
            soFile.copyTo(destination, overwrite = true)
        }
    }
}

tasks.register<Zip>("zip") {
    dependsOn("copyFiles")

    archiveFileName.set("PhotosUnlimited.zip")
    destinationDirectory.set(project.rootDir.resolve("out"))

    from(project.rootDir.resolve("module"))
}

afterEvaluate {
    tasks["assembleRelease"].finalizedBy("copyFiles", "zip")
}
