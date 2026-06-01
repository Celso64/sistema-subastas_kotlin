package org.example.prueba_kotlin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.prueba_kotlin.desktopApp.view.App
import org.example.prueba_kotlin.shared.di.SharedModule
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import prueba_kotlin.composeapp.generated.resources.Res
import prueba_kotlin.composeapp.generated.resources.logo
import java.nio.file.Files
import java.nio.file.Paths

fun main() = application {
    startKoin {
        printLogger(Level.ERROR)
        modules(SharedModule)
    }

    val rutaDirectorio = Paths.get("data")
    val rutaImg = Paths.get("data/img")

    try {
        Files.createDirectories(rutaDirectorio)
        Files.createDirectories(rutaImg)
    } catch (e: Exception) {
        println("Error al crear la carpeta: ${e.message}")
    }


    Window(
        onCloseRequest = ::exitApplication,
        title = "Subastas",
        icon = painterResource(Res.drawable.logo)
    ) {
        App()
    }
}