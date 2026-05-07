package org.example.prueba_kotlin.desktopApp.view

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AllInbox
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.People
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.prueba_kotlin.desktopApp.view.tab_comprador.CompradorPanel
import org.example.prueba_kotlin.desktopApp.view.tab_lote.LotePanel

@Composable
fun Tabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Compradores", "Lotes")
    val icons = listOf(Icons.Rounded.People, Icons.Rounded.AllInbox)


    Column(modifier = Modifier.fillMaxSize()) {
        // 2. La fila de pestañas (TabRow)
        PrimaryTabRow(selectedTabIndex = selectedTabIndex) {

            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = "Agregar"
                            )
                            Text(title)
                        }
                    }
                )
            }
        }

        // 3. El panel que cambia en función de la tab
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTabIndex) {
                0 -> CompradorPanel()
                1 -> LotePanel()
            }
        }
    }
}
