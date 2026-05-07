package org.example.prueba_kotlin.shared.service

import org.example.prueba_kotlin.shared.model.ItemLote
import org.example.prueba_kotlin.shared.model.Lote
import org.example.prueba_kotlin.shared.repository.LoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Objects
import java.util.UUID

class LoteService(private val repository: LoteRepository): KoinComponent {

    private val lotes: MutableMap<UUID, Lote> = mutableMapOf()
    private val compradorService: CompradorService by inject()

    fun get_lotes(): List<Lote>{
        return repository.findAll()
    }

    fun add_lote(items: Map<String, Int>, id_comprador: String, descripcion: String = ""){


        val comprador = compradorService.find_by_id(id_comprador)

        if(Objects.nonNull(comprador)){
            val lote_nuevo = comprador?.let { Lote(items = generateItems(items), descripcion = descripcion, comprador = it) }
            if (lote_nuevo != null) {
                repository.save(lote_nuevo)
            }
        }


//
//        println("LOTE_SERVICE | COMPRADOR: ${comprador.toString()}")
//
//        if (Objects.nonNull(comprador)){
//            val items_creados = generateItems(items)
//            val lote_nuevo = comprador?.let { Lote(items= items_creados, descripcion = descripcion, comprador = it) }
//            lotes[lote_nuevo?.id as UUID] = lote_nuevo
//        }

    }

    private fun generateItems(items: Map<String, Int>): Set<ItemLote>{
        val lista_items = mutableSetOf<ItemLote>()

        items.forEach { (k, v) ->
            run {
                lista_items.add(ItemLote(nombre = k, precio = v))
            }
        }

        return lista_items.toSet()
    }
}