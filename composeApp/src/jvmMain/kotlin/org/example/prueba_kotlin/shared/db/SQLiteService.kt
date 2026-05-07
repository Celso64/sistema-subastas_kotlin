package org.example.prueba_kotlin.shared.db

import java.sql.Connection
import java.sql.DriverManager

open class SQLiteService(private val dbPath: String): DBService {

    init {
        initDatabase()
    }

    override fun connect(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:$dbPath")
    }

    override fun initDatabase() {
        connect().use { conn ->
            SQLScripts.creates.forEach { c -> conn.createStatement().execute(c) }
            SQLScripts.inserts.forEach { i -> conn.prepareStatement(i).executeUpdate() }
        }
    }
}