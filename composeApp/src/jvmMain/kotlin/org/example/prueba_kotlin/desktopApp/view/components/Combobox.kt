package org.example.prueba_kotlin.desktopApp.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Combobox(datos: List<ComboboxDatos>, set_id_seleccionado: (id: String) -> Unit, texto_label: String = "Elige una opcion") {

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(datos[0]) }
    set_id_seleccionado(selectedOption.id)

    Box() {
        OutlinedTextField(
            value = selectedOption.texto,
            onValueChange = { },
            readOnly = true, // Makes it behave like a classic ComboBox
            label = { Text(texto_label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Ver Opciones"
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            datos.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = selectionOption
                        set_id_seleccionado(selectedOption.id)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption.texto)
                }
            }
        }
    }
}