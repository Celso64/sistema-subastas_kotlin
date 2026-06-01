package org.example.prueba_kotlin.desktopApp.view.tab_comprador

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.example.prueba_kotlin.shared.service.CompradorService
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Drawable
import prueba_kotlin.composeapp.generated.resources.Res
import prueba_kotlin.composeapp.generated.resources.logo
import prueba_kotlin.composeapp.generated.resources.user_placeholder
import java.awt.image.BufferedImage
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Formatter
import java.util.Objects
import javax.imageio.ImageIO

@Composable
fun CompradorDetalle(service: CompradorService, id_comprador: String, onBack: () -> Unit){
    val comprador = service.find_by_id(id_comprador)

    val fecha_formateada = LocalDate.parse(comprador?.fecha_creacion).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    val imagen_default = Res.drawable.user_placeholder

    Column {
        Row {
            Button(onClick = { onBack() }){
                Text("Volver")
            }
        }
        Row {
            if (comprador != null && comprador.imagen != null) {
                Image(bitmap = ImageIO.read(comprador.imagen).toComposeImageBitmap(),
                    contentDescription = "Avatar local del proyecto",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape))
            } else {
                Image(painter = painterResource(imagen_default),
                    contentDescription = "Avatar local del proyecto",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape))
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(comprador?.nombre ?: "")
                }
                Row {
                    Text("ID Unico: ${comprador?.id.toString()}")
                }
                Row {
                    Text("Contacto: ${comprador?.contacto ?: ""}")
                }
                Row {
                    Text("Desde: ${fecha_formateada ?: ""}")
                }
            }
        }
    }


}