package org.example.prueba_kotlin.desktopApp.view.tab_comprador

sealed class Screen {
    object Table : Screen()
    object Form : Screen()
}