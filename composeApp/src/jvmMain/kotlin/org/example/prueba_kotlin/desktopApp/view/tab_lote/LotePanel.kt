package org.example.prueba_kotlin.desktopApp.view.tab_lote

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.prueba_kotlin.shared.service.CompradorService
import org.example.prueba_kotlin.shared.service.LoteService
import org.example.prueba_kotlin.desktopApp.view.tab_comprador.Screen
import org.koin.compose.koinInject

@Composable
fun LotePanel() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Table) }
    val loteService = koinInject<LoteService>()
    val compradorService = koinInject<CompradorService>()

    MaterialTheme() {
        Surface {
            Crossfade(targetState = currentScreen) { screen ->
                when (screen) {
                    is Screen.Table -> LoteTable(
                        loteService = loteService,
                        onNavigateToForm = { currentScreen = Screen.Form }
                    )
                    is Screen.Form -> LoteForm(
                        loteService = loteService,
                        onBack = { currentScreen = Screen.Table },
                        compradorService = compradorService
                    )
                }
            }
        }
    }
}