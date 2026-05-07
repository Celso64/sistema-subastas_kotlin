package org.example.prueba_kotlin.shared.model

import org.example.prueba_kotlin.shared.constant.FormatoFecha
import java.time.LocalDate
import java.util.UUID

data class Lote(
    val id: UUID = UUID.randomUUID(),
    val items: Set<ItemLote>,
    val comprador: Comprador,
    val descripcion: String = "",
    val fecha_creacion: String = LocalDate.now().format(FormatoFecha.DEFAULT),
    var fecha_vencimiento: String = LocalDate.now().plusWeeks(2).format(FormatoFecha.DEFAULT),
    var estado: EstadoLote = EstadoLote.pendiente
){
    fun precio_total(): Int{
        if(items.isEmpty()){
            return 0
        }
        return items.sumOf { i -> i.precio }
    }

    fun vencido(): Boolean{
        val vencimiento: LocalDate = LocalDate.parse(this.fecha_vencimiento, FormatoFecha.DEFAULT)
        return LocalDate.now().isAfter(vencimiento) && estado.equals(EstadoLote.pendiente)
    }
}