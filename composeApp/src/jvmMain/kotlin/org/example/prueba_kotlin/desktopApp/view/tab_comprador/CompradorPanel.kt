package org.example.prueba_kotlin.desktopApp.view.tab_comprador

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.example.prueba_kotlin.desktopApp.view.tab_comprador.form.CompradorForm
import org.example.prueba_kotlin.shared.service.CompradorService
import org.koin.compose.koinInject


@Composable
fun CompradorPanel() {
    val compradorService = koinInject<CompradorService>()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Table) }

    val compradores by compradorService.uiState.collectAsState()

    MaterialTheme() {
        Surface {
            Crossfade(targetState = currentScreen) { screen ->
                when (screen) {
                    is Screen.Table -> CompradorTable(
                        compradores = compradores.toList(),
                        onNavigateToForm = { currentScreen = Screen.Form }
                    )
                    is Screen.Form -> CompradorForm(
                        compradorService = compradorService,
                        onBack = { currentScreen = Screen.Table }
                    )
                }
            }
        }
    }
}