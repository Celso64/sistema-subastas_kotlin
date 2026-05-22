package org.example.prueba_kotlin.desktopApp.view.tab_lote.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.prueba_kotlin.shared.model.Comprador
import org.example.prueba_kotlin.shared.service.CompradorService
import org.example.prueba_kotlin.shared.service.LoteService
import org.example.prueba_kotlin.desktopApp.view.components.Combobox
import org.example.prueba_kotlin.desktopApp.view.components.ComboboxDatos
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun LoteForm(loteService: LoteService, compradorService: CompradorService, onBack: () -> Unit) {

    var descripcion by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    val items = remember { mutableStateMapOf<Int, LoteItemData>() }
    var invalido by remember { mutableStateOf("") }

    val compradores by compradorService.uiState.collectAsState()

    val items_combo: List<ComboboxDatos> = list_to_data(compradores.toList())

    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = !invalido.isEmpty(),
        enter = fadeIn() + expandVertically(), // Efecto de entrada
        exit = fadeOut() + shrinkVertically()  // Efecto de salida
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.Black).alpha(1.0f)) {
            Snackbar(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Campos invalidos: $invalido", modifier = Modifier.weight(9f), textAlign = TextAlign.Center)
                    androidx.compose.material3.TextButton(onClick = { invalido = "" }, modifier = Modifier.weight(1f)) {
                        Text("X", color = Color.White, textAlign = TextAlign.End)
                    }
                }
            }
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Obtenemos el inicio del día de hoy en UTC para comparar limpiamente
                val inicioHoyUtc = LocalDate.now()
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli()

                // Solo dejamos seleccionar si la fecha del calendario es hoy o en el futuro
                return utcTimeMillis >= inicioHoyUtc
            }

            override fun isSelectableYear(year: Int): Boolean {
                // Opcional: No dejar que naveguen a años pasados en el desplegable
                return year >= LocalDate.now().year
            }
        }
    )

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
                    .fillMaxSize()
            )
            {
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

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                LoteItemForm(
                    items = items.values.toList(),
                    add_item = { i: LoteItemData -> agregar_carta(i, items) },
                    remove_item = { i -> items.remove(i) }
                )
            }

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.height(500.dp)
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
                val instant = Instant.ofEpochMilli(datePickerState.selectedDateMillis?: -1)
                val fecha: LocalDate = LocalDate.ofInstant(instant, ZoneId.systemDefault())
                println(fecha.toString())
                val items_list = items.values.toList()
                val valido = LoteData(fecha, items_list).es_valido()
                invalido = valido
                if(valido.isEmpty()){
                    val items_map = to_map(items_list)
                    loteService.add_lote(
                        items = items_map,
                        id_comprador = id,
                        descripcion = descripcion,
                        fecha_vencimiento = fecha
                    )
                    onBack()
                }
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

private fun agregar_carta(carta: LoteItemData, items: SnapshotStateMap<Int, LoteItemData>): Unit {
    val (indice, nombre, precio) = carta

    if (!nombre.isEmpty() && !precio.isEmpty()) {
        items[indice] = carta
    }
}

private fun to_map(items: List<LoteItemData>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    items.forEach { i: LoteItemData -> map[i.nombre] = i.precio.toInt() }
    return map.toMap()
}