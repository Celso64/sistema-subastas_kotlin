package org.example.prueba_kotlin.desktopApp.view.tab_lote.form

import java.time.LocalDate

data class LoteData(val vencimiento: LocalDate, val items: List<LoteItemData>) {

    fun es_valido(): String{
        val lista_errores = mutableListOf<String>()

        if(vencimiento.isBefore(LocalDate.now())){
            lista_errores.add("No se puede usar una fecha anterior a la actual")
        }
        if(items.isEmpty()){
            lista_errores.add("Debe haber al menos un item")
        }
        if(!lista_errores.isEmpty()) {
            return lista_errores.toList().joinToString(separator = "; ") + "."
        }
        return ""
    }
}