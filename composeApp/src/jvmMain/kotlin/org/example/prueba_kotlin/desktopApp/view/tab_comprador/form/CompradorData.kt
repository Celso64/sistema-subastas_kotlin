package org.example.prueba_kotlin.desktopApp.view.tab_comprador.form

data class CompradorData(private val nombre: String, private val contacto: String){
    fun es_valido(): String{
        val lista_errores = mutableListOf<String>()

        if(nombre.isBlank()){
            lista_errores.add("Nombre no puede estar vacio")
        }
        if(contacto.isBlank()){
            lista_errores.add("Contacto no puede estar vacio")
        }



        if(!lista_errores.isEmpty()) {
            return lista_errores.toList().joinToString(separator = "; ") + "."
        }
        return ""
    }
}
