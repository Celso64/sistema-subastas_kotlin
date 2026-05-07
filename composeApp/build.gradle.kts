import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting{
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                // Usá preferentemente las del version catalog (libs)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)

                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                implementation("com.ryinex.kotlin:compose-data-table:1.0.7")
                implementation("io.insert-koin:koin-core:3.5.6")
                implementation("io.insert-koin:koin-compose:1.1.5")
                implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

                // Quitamos las duplicadas manuales y el sqlite de aquí
                implementation("org.slf4j:slf4j-simple:2.0.12")
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.desktop.linux_x64)
                implementation(compose.desktop.windows_x64)
                implementation(compose.desktop.macos_x64)
                implementation(compose.desktop.macos_arm64)
                
                implementation(libs.kotlinx.coroutinesSwing)
                implementation("org.xerial:sqlite-jdbc:3.45.1.0")
            }
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.example.prueba_kotlin.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "sistema_subasta"
            packageVersion = "1.2.1"
        }
    }
}