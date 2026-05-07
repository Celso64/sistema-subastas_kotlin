package org.example.prueba_kotlin.desktopApp.view.tab_comprador.form

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.prueba_kotlin.shared.service.CompradorService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusTargetModifierNode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.example.prueba_kotlin.desktopApp.view.Colores

@Composable
fun CompradorForm(compradorService: CompradorService, onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var invalido by remember { mutableStateOf("") }


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


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(Modifier.height(24.dp))
        Text("Nuevo Comprador", style = MaterialTheme.typography.h5)
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
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Aqui iria el componente de agregar imagen.")
            }

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nuevoTexto: String -> nombre = nuevoTexto },
                    label = { Text("Nombre") },
                )

                OutlinedTextField(
                    value = contacto,
                    onValueChange = { nuevoTexto: String -> contacto = nuevoTexto },
                    label = { Text("Contacto") }
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
                val valid = CompradorData(nombre, contacto).es_valido()
                if (valid.isEmpty()) {
                    compradorService.add_comprador(nombre = nombre, contacto = contacto)
                    onBack()
                } else {
                    invalido = valid
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