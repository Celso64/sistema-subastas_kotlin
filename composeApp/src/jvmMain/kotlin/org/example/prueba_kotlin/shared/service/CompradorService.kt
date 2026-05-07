package org.example.prueba_kotlin.shared.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.prueba_kotlin.shared.model.Comprador
import org.example.prueba_kotlin.shared.repository.CompradorRepository
import java.util.UUID

class CompradorService(private val repository: CompradorRepository) {
    private val _uiState = MutableStateFlow(repository.findAll())

    val uiState = _uiState.asStateFlow()

    fun find_by_id(id: String): Comprador?{
        return repository.findById(id)
    }

    fun add_comprador(nombre: String, contacto: String) {
        val nuevo_comprador = Comprador(nombre = nombre, contacto = contacto)
        repository.save(nuevo_comprador)
        update_list()
    }

    private fun update_list(){
        _uiState.update { repository.findAll() }
    }
}