package org.example.prueba_kotlin.shared.model

import java.util.UUID

data class ItemLote(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val precio: Int = 0
)
