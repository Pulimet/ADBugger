import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "net.alexandroid.adbugger"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.macos_arm64)
                implementation("com.malinskiy.adam:adam:0.5.1")

                // Icons Packs
                listOf(
                    "simple-icons",
                    "feather",
                    "tabler-icons",
                    "eva-icons",
                    "font-awesome",
                    "octicons",
                    "linea",
                    "line-awesome",
                    "erikflowers-weather-icons",
                    "css-gg"
                ).forEach {
                    implementation("br.com.devsrsouza.compose.icons.jetbrains:$it:1.0.0")
                }
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        //javaHome = System.getenv("JDK_18")
        javaHome = "/Users/ak3140/Library/Java/JavaVirtualMachines/openjdk-18.0.1.1/Contents/Home"
        buildTypes.release.proguard {
            obfuscate.set(false)
            optimize.set(false)
            configurationFiles.from(project.file("rules.pro"))
        }
        nativeDistributions {
            outputBaseDir.set(project.buildDir.resolve("output"))
            targetFormats(TargetFormat.Dmg/*, TargetFormat.Msi*/)
            packageName = "ADBugger"
            packageVersion = "1.0.0"

            macOS {
                bundleID = "net.alexandroid.adbugger"
                // a version for all macOS distributables
                packageVersion = "1.0.0"
                // a version only for the dmg package
                dmgPackageVersion = "1.0.0"
                // a version only for the pkg package
                pkgPackageVersion = "1.0.0"

                // a build version for all macOS distributables
                packageBuildVersion = "1.0.0"
                // a build version only for the dmg package
                dmgPackageBuildVersion = "1.0.0"
                // a build version only for the pkg package
                pkgPackageBuildVersion = "1.0.0"
            }
        }
    }
}
