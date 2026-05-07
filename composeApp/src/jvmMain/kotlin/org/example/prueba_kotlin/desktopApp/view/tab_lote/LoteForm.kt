package org.example.prueba_kotlin.desktopApp.view.tab_lote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.prueba_kotlin.shared.model.Comprador
import org.example.prueba_kotlin.shared.service.CompradorService
import org.example.prueba_kotlin.shared.service.LoteService
import org.example.prueba_kotlin.desktopApp.view.components.Combobox
import org.example.prueba_kotlin.desktopApp.view.components.ComboboxDatos

@Composable
fun LoteForm(loteService: LoteService, compradorService: CompradorService, onBack: () -> Unit) {

    var descripcion by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    val items = remember { mutableStateMapOf<Int, LoteItemData>() }

    val compradores by compradorService.uiState.collectAsState()

    val items_combo: List<ComboboxDatos> = list_to_data(compradores.toList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nuevo Lote", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min) // Importante para que el Divider ocupe la altura del contenido
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                    Text("Datos del lote", style = MaterialTheme.typography.h6)

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { nuevoTexto: String -> descripcion = nuevoTexto },
                        label = { Text("Descripcion") }
                    )

                    Spacer(Modifier.height(4.dp))

                    Combobox(
                        datos = items_combo,
                        set_id_seleccionado = { t: String -> id = t },
                        texto_label = "Elija un comprador"
                    )
                }
            }

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 16.dp)
            ) {
                LoteItemForm(
                    items = items.values.toList(),
                    add_item = {i: LoteItemData -> agregar_carta(i, items)},
                    remove_item = {i -> items.remove(i)}
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.5f)
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                loteService.add_lote(
                    items = to_map(items.values.toList()),
                    id_comprador = id,
                    descripcion = descripcion
                )
                onBack()
            }) {
                Text("Guardar")
            }
            TextButton(onClick = onBack) {
                Text("Cancelar")
            }
        }
    }

}

private fun list_to_data(compradores: List<Comprador>): List<ComboboxDatos> {
    return compradores.map { c -> ComboboxDatos(c.id.toString(), c.nombre) }.toList()
}

private fun agregar_carta(carta: LoteItemData, items: SnapshotStateMap<Int, LoteItemData>): Unit{
    val (indice, nombre, precio) = carta

    if(!nombre.isEmpty() && !precio.isEmpty()){
        items[indice] = carta
    }
}

private fun to_map(items: List<LoteItemData>): Map<String, Int>{
    val map = mutableMapOf<String, Int>()
    items.forEach { i: LoteItemData -> map[i.nombre] = i.precio.toInt() }
    return map.toMap()
}