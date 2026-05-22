package org.example.prueba_kotlin.desktopApp.view.tab_lote.form

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoteItemForm(
    items: List<LoteItemData>,
    add_item: (item: LoteItemData) -> Unit,
    remove_item: (indice: Int) -> Unit
) {

    var contador by remember { mutableStateOf(0) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Agregar Carta", style = MaterialTheme.typography.h6)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nuevoTexto: String -> nombre = nuevoTexto },
                label = { Text("Nombre de Carta") }
            )

            Spacer(Modifier.height(4.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { nuevoTexto: String ->
                    if (nuevoTexto.all { it.isDigit() }) {
                        precio = nuevoTexto
                    }
                },
                label = { Text("Precio") }
            )

            Spacer(Modifier.height(4.dp))

            Button(onClick = {
                add_item(LoteItemData(indice = contador++, nombre = nombre, precio = precio))
                nombre = ""
                precio = ""
            }) {
                Text("Agregar")
            }
        }

        Column(modifier = Modifier.weight(1f).height(300.dp).padding(4.dp)) {
            Box(modifier = Modifier.fillMaxHeight()) { // Tamaño fijo para evitar el error de Intrinsic
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(end = 8.dp)
                ) {
                    if (items.size > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Color.Gray).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Text("Nombre", modifier = Modifier.weight(0.3f))
                            Text("Precio", modifier = Modifier.weight(0.3f))
                            Text("Accion", modifier = Modifier.weight(0.3f))
                        }
                    }

                    items.forEach { i -> Elemento(i, remove_item) }
                }

                // La barra de scroll característica de Desktop
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(scrollState)
                )
            }
        }
    }
}

@Composable
fun Elemento(item: LoteItemData, funcion: (i: Int) -> Unit){
    val colorFila: Color = if (item.indice % 2 == 0)  Color.White else Color.LightGray
    Row(modifier = Modifier.fillMaxWidth().background(colorFila).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(item.nombre, modifier = Modifier.weight(0.3f))
        Text(item.precio, modifier = Modifier.weight(0.3f))
        TextButton(onClick = { funcion(item.indice) }, modifier = Modifier.weight(0.3f)){
            Text("Quitar")
        }
    }
}