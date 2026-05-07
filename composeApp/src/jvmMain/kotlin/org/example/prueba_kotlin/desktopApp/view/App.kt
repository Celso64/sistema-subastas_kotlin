package org.example.prueba_kotlin.desktopApp.view

import androidx.compose.material.lightColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val MisColores = lightColors(
        primary = Color(0xFF2196F3),       // Un azul moderno
        primaryVariant = Color(0xFF1976D2),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFFF5F5F5),    // Gris muy claro para el fondo
        surface = Color.White,
        onPrimary = Color.White            // Color del texto sobre el primario
    )

    MaterialTheme(colors = MisColores) {
        Tabs()
    }
}