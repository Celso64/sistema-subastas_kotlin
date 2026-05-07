package org.example.prueba_kotlin.shared.constant

import java.time.format.DateTimeFormatter

object FormatoFecha {
    val DEFAULT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
}