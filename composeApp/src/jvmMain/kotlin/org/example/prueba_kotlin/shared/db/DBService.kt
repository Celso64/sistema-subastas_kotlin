package org.example.prueba_kotlin.shared.db

import java.sql.Connection

interface DBService {
    fun connect(): Connection
    fun initDatabase()
}