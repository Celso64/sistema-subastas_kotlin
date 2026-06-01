package org.example.prueba_kotlin.desktopApp.view.tab_comprador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Pageview
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ViewColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import org.example.prueba_kotlin.shared.model.Comprador

@Composable
fun CompradorTable(compradores: List<Comprador>, onNavigateToForm: () -> Unit, onDetalle: (id_comprador: String) -> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Compradores", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(24.dp))

        Button(onClick = onNavigateToForm) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Agregar"
            )
            Spacer(modifier = Modifier.width(1.dp))
            Text("Agregar Comprador")
        }

        Row(
            modifier = Modifier.background(Color.LightGray).padding(8.dp)

        ) {
            Text("Nombre", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Contacto", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Acciones", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(compradores) { comprador: Comprador ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(comprador.nombre, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(comprador.contacto, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)


                    Button(onClick = { onDetalle(comprador.id.toString()) },
                        modifier = Modifier.weight(1f)
                        ) {
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
