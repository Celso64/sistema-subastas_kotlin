package org.example.prueba_kotlin.desktopApp.view

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(cuerpo: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text(cuerpo) },
        confirmButton = {
            Button(onClick = onDismiss) { Text("OK") }
        }
    )
}