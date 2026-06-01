package org.example.prueba_kotlin.shared.model

import java.io.File
import java.time.LocalDate
import java.util.UUID

data class Comprador(
    val id: UUID = UUID.randomUUID(),
    var nombre: String,
    var contacto: String,
    val fecha_creacion: String = LocalDate.now().toString(),
    val imagen: File? = null
)