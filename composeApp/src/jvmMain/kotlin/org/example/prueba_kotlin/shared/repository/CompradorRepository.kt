package org.example.prueba_kotlin.shared.repository

import org.example.prueba_kotlin.shared.db.DBService
import org.example.prueba_kotlin.shared.model.Comprador
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.sql.SQLException
import java.util.Objects
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

        if(existsByName(comprador.nombre)){
            throw RuntimeException("El nombre debe ser unico")
        }

        var nombre_imagen: String? = null

        if(Objects.nonNull(comprador.imagen)){
            val nombre_archivo = comprador.imagen?.name.toString().split(".")
            val nombre = nombre_archivo[0]
            val extension = nombre_archivo[1]
            nombre_imagen = "${comprador.id}.$extension"
            val f = FileOutputStream("data/img/$nombre_imagen")
            f.write(comprador.imagen?.readBytes())
        }


        val sql = "INSERT INTO comprador (id, nombre, contacto, fecha_creacion, imagen_nombre) VALUES (?, ?, ?, ?, ?)"

        db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.setString(2, comprador.nombre)
                pstmt.setString(3, comprador.contacto)
                pstmt.setString(4, comprador.fecha_creacion)
                pstmt.setString(5, nombre_imagen)
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
                        val imagen_nombre: String? = rs.getString("imagen_nombre")
                        var file: File? = null
                        if(Objects.nonNull(imagen_nombre)){
                            file = File("data/img/${imagen_nombre?: ""}")
                        }
                        println(file.toString())
                        Comprador(
                            id = UUID.fromString(rs.getString("id")),
                            nombre = rs.getString("nombre"),
                            contacto = rs.getString("contacto"),
                            fecha_creacion = rs.getString("fecha_creacion"),
                            imagen = file
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

    fun existsByName(nombre: String): Boolean {
        val sql = "SELECT 1 FROM comprador WHERE nombre = ? LIMIT 1"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, nombre)
                pstmt.executeQuery().use { rs ->
                    rs.next()
                }
            }
        }
    }
}