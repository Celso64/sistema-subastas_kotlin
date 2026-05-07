package org.example.prueba_kotlin.desktopApp.view.tab_lote

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Pageview
import androidx.compose.material.icons.rounded.SortByAlpha
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ToggleButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.example.prueba_kotlin.desktopApp.view.Colores
import org.example.prueba_kotlin.shared.model.ItemLote
import org.example.prueba_kotlin.shared.service.LoteService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoteTable(loteService: LoteService, onNavigateToForm: () -> Unit) {
    var listaProductos by remember { mutableStateOf(loteService.get_lotes())}

    var tg_filter by remember { mutableStateOf(false) }
    var tg_sort by remember { mutableStateOf(false) }

    var filtro by remember { mutableStateOf("") }
    var ordenAscendente by remember { mutableStateOf(true) }

    // LÓGICA DE FILTRADO Y ORDENAMIENTO
    val listaFiltrada = remember(listaProductos, filtro, ordenAscendente) {
        listaProductos
            .filter { it.comprador.nombre.contains(filtro, ignoreCase = true) }
            .sortedBy { if (ordenAscendente) it.precio_total() else -it.precio_total() }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Lotes", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            Button(onClick = onNavigateToForm) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Agregar"
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text("Agregar Lote")
            }
            ToggleButton(
                checked = tg_filter,
                onCheckedChange = { b -> tg_filter = b },
                modifier = Modifier,
                enabled = true,
            ){
                Icon(
                    imageVector = Icons.Rounded.FilterAlt,
                    contentDescription = "Agregar"
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text("Filtrar")
            }
            ToggleButton(
                checked = tg_sort,
                onCheckedChange = { b -> tg_sort = b },
                modifier = Modifier,
                enabled = true,
            ){
                Icon(
                    imageVector = Icons.Rounded.SortByAlpha,
                    contentDescription = "Agregar"
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text("Ordenar")
            }
        }

        AnimatedVisibility(
            visible = tg_filter,
            enter = fadeIn() + expandVertically(), // Efecto de entrada
            exit = fadeOut() + shrinkVertically()  // Efecto de salida
        ){
            TextField(
                value = filtro,
                onValueChange = { filtro = it },
                label = { Text("Filtrar por comprador") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }

        AnimatedVisibility(
            visible = tg_sort,
            enter = fadeIn() + expandVertically(), // Efecto de entrada
            exit = fadeOut() + shrinkVertically()  // Efecto de salida
        ){
            Button(onClick = { ordenAscendente = !ordenAscendente }) {
                Text("Ordenar por Precio: ${if (ordenAscendente) "Asc" else "Desc"}")
            }
        }

        Spacer(Modifier.height(8.dp))

        // ENCABEZADO DE TABLA
        Row(Modifier.background(Color.LightGray).padding(8.dp)) {
            Text("Nombre", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Precio", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Comprador", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Fecha de Creacion", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Fecha de Vencimiento", Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Acciones", Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        // CUERPO DE LA TABLA
        AnimatedContent(
            targetState = listaFiltrada, // Al cambiar la lista, se dispara la animación
            transitionSpec = {
                fadeIn(animationSpec = tween(600)) togetherWith  fadeOut(animationSpec = tween(600))
            }
        ){
        LazyColumn {
            items(listaFiltrada) { lote ->
                val color_fila: Color = if (lote.vencido()) Colores.LOTE_VENCIDO else Colores.LOTE_ACTIVO

                Row(
                    Modifier.height(36.dp).animateItem().background(color = color_fila),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(get_cartas_nombre(lote.items), Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text("$${lote.precio_total()}", Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(lote.comprador.nombre, Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(lote.fecha_creacion, Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(lote.fecha_vencimiento, Modifier.weight(1f), textAlign = TextAlign.Center)
                    Button(onClick = { nop() }, modifier = Modifier.weight(1f).width(24.dp)){
                        Icon(
                            imageVector = Icons.Rounded.Pageview,
                            contentDescription = "Agregar"
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text("Ver", textAlign = TextAlign.Center)
                    }
                }
            }
        }
        }
    }
}

private fun nop(): Unit{
    return
}

private fun get_cartas_nombre(cartas: Set<ItemLote>): String{
    var res = ""
    val c = cartas.toList()
    when(cartas.size){
        1 -> res = "1 carta"
        else -> res = "${cartas.size} cartas"
    }
    return res
}

private fun vencido(fecha: String): Boolean{
    val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val f = LocalDate.parse(fecha, dtf)

    return LocalDate.now().isAfter(f)
}