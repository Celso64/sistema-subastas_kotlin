package org.example.prueba_kotlin.shared.di

import org.example.prueba_kotlin.shared.db.DBService
import org.example.prueba_kotlin.shared.db.SQLiteService
import org.example.prueba_kotlin.shared.repository.CompradorRepository
import org.example.prueba_kotlin.shared.repository.LoteRepository
import org.example.prueba_kotlin.shared.service.CompradorService
import org.example.prueba_kotlin.shared.service.LoteService
import org.koin.dsl.module

val SharedModule = module {

    single { "mi_database.db" }
    single<DBService> { SQLiteService(dbPath = get()) }

    single { CompradorRepository(db_service = get()) }
    single { LoteRepository(db_service = get()) }

    single { LoteService(get()) }
    single { CompradorService(get()) }
}