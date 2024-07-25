import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

val appVersion = "1.0.2"

group = "net.alexandroid.adbugger"
version = appVersion

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    // Koin
    implementation("co.touchlab:stately-concurrent-collections:2.0.6")
    implementation("io.insert-koin:koin-core:3.6.0-wasm-alpha2")
    implementation("io.insert-koin:koin-compose:1.2.0-Beta4")

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
        implementation("br.com.devsrsouza.compose.icons:$it:1.1.0")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        buildTypes.release.proguard {
            obfuscate.set(false)
            optimize.set(false)
            configurationFiles.from(project.file("rules.pro"))
        }

        nativeDistributions {
            outputBaseDir.set(project.buildDir.resolve("output"))
            targetFormats(TargetFormat.Dmg/*, TargetFormat.Msi*/)
            packageName = "ADBugger"
            packageVersion = appVersion
            description = "ADBugger is a tool for Android Developers to debug their apps on real devices and emulators."
            copyright = "© 2024 Alexey Korolev. All rights reserved."
            vendor = "Alexey Korolev"
            // licenseFile.set(project.file("LICENSE.txt"))

            macOS {
                bundleID = "net.alexandroid.adbugger"
                // a version for all macOS distributables
                packageVersion = appVersion
                // a version only for the dmg package
                dmgPackageVersion = appVersion
                // a version only for the pkg package
                pkgPackageVersion = appVersion

                // a build version for all macOS distributables
                packageBuildVersion = appVersion
                // a build version only for the dmg package
                dmgPackageBuildVersion = appVersion
                // a build version only for the pkg package
                pkgPackageBuildVersion = appVersion

                //Icon
                val iconsRoot = project.file("src/main/resources")
                iconFile.set(iconsRoot.resolve("macos.icns"))
            }
        }
    }
}
