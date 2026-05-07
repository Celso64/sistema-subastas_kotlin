package org.example.prueba_kotlin.shared.repository

import org.example.prueba_kotlin.shared.db.DBService
import org.example.prueba_kotlin.shared.model.Comprador
import java.util.UUID

class CompradorRepository(private val db_service: DBService) {

    fun save(comprador: Comprador){

        var id = comprador.id.toString()

        val existe = existsById(comprador.id.toString())

        if(existe){
            do{
                id = UUID.randomUUID().toString()
            }while (comprador.id.toString().equals(id))
        }

        val sql = "INSERT INTO comprador (id, nombre, contacto, fecha_creacion) VALUES (?, ?, ?, ?)"

        // .use asegura que la conexión y el statement se cierren al terminar
        db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.setString(2, comprador.nombre)
                pstmt.setString(3, comprador.contacto)
                pstmt.setString(4, comprador.fecha_creacion)
                pstmt.executeUpdate()
            }
        }
    }

    fun findAll(): List<Comprador> {
        val usuarios = mutableListOf<Comprador>()
        val sql = "SELECT * FROM comprador"

        db_service.connect().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use { rs ->
                    while (rs.next()) {
                        usuarios.add(
                            Comprador(
                                id = UUID.fromString(rs.getString("id")),
                                nombre = rs.getString("nombre"),
                                contacto = rs.getString("contacto"),
                                fecha_creacion = rs.getString("fecha_creacion"),
                            )
                        )
                    }
                }
            }
        }
        return usuarios
    }

    fun findById(id: String): Comprador? {
        val sql = "SELECT * FROM comprador WHERE id = ?"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        Comprador(
                            id = UUID.fromString(rs.getString("id")),
                            nombre = rs.getString("nombre"),
                            contacto = rs.getString("contacto"),
                            fecha_creacion = rs.getString("fecha_creacion"),
                        )
                    } else {
                        null // Si no se encuentra el ID
                    }
                }
            }
        }
    }

    fun existsById(id: String): Boolean {
        val sql = "SELECT 1 FROM comprador WHERE id = ? LIMIT 1"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.executeQuery().use { rs ->
                    rs.next()
                }
            }
        }
    }
}