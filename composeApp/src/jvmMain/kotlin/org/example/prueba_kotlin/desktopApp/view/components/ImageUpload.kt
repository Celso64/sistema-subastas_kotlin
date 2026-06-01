package org.example.prueba_kotlin.desktopApp.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import javax.imageio.ImageIO

@Composable
fun ImageForm(onImageSaved: (File) -> Unit) {
    var selectedBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Visor de la imagen seleccionada
        if (selectedBitmap != null) {
            Image(
                bitmap = selectedBitmap!!,
                contentDescription = "Vista previa",
                modifier = Modifier.size(200.dp).padding(bottom = 16.dp)
            )
        } else {
            Text("Ninguna imagen seleccionada", modifier = Modifier.padding(bottom = 16.dp))
        }

        // 2. Botón para abrir el diálogo nativo
        Button(onClick = {
            openFileDialog { file ->
                selectedFile = file
                selectedBitmap = ImageIO.read(file).toComposeImageBitmap()
                onImageSaved(selectedFile!!)
            }
        }) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Función auxiliar para invocar el FileDialog de AWT
private fun openFileDialog(onFileSelected: (File) -> Unit) {
    val dialog = FileDialog(null as Frame?, "Seleccionar Imagen", FileDialog.LOAD).apply {
        // Filtro para que solo muestre imágenes comunes
        setFilenameFilter { _, name ->
            val lower = name.lowercase()
            lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")
        }
        isVisible = true
    }

    if (dialog.directory != null && dialog.file != null) {
        onFileSelected(File(dialog.directory, dialog.file))
    }
}