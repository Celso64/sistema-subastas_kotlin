package org.example.prueba_kotlin.shared.repository

import org.example.prueba_kotlin.shared.db.DBService
import org.example.prueba_kotlin.shared.model.Comprador
import org.example.prueba_kotlin.shared.model.ItemLote
import org.example.prueba_kotlin.shared.model.Lote
import java.util.UUID

class LoteRepository (private val db_service: DBService) {

    fun save(lote: Lote){

        var id = lote.id.toString()

        val existe = existsById(lote.id.toString())

        if(existe){
            do{
                id = UUID.randomUUID().toString()
            }while (lote.id.toString().equals(id))
        }

        val id_estado: String = get_initial_state()

        val sql = "INSERT INTO lote (id, descripcion, fecha_creacion, fecha_vencimiento, id_comprador, id_estado) VALUES (?, ?, ?, ?, ?, ?)"

        db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.setString(2, lote.descripcion)
                pstmt.setString(3, lote.fecha_creacion.toString())
                pstmt.setString(4, lote.fecha_vencimiento.toString())
                pstmt.setString(5, lote.comprador.id.toString())
                pstmt.setString(6, id_estado)
                pstmt.executeUpdate()
            }
        }

        create_items(lote.items, id)
    }

    fun findAll(): List<Lote> {
        val lote = mutableListOf<Lote>()
        val sql = "SELECT l.id AS id, l.descripcion AS descripcion, l.fecha_creacion AS fecha_creacion, l.fecha_vencimiento AS fecha_vencimiento, " +
                " c.id AS id_comprador, c.nombre AS nombre, c.contacto AS contacto, c.fecha_creacion AS comprador_creacion " +
                " FROM lote l " +
                " LEFT JOIN comprador c ON l.id_comprador == c.id"

        db_service.connect().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use { rs ->
                    while (rs.next()) {

                        val items_lote = find_items_by_lote_id(rs.getString("id"))

                        lote.add(
                            Lote(
                                id = UUID.fromString(rs.getString("id")),
                                descripcion = rs.getString("descripcion"),
                                comprador = Comprador(
                                    id = UUID.fromString(rs.getString("id_comprador")),
                                    nombre = rs.getString("nombre"),
                                    contacto = rs.getString("contacto"),
                                    fecha_creacion = rs.getString("comprador_creacion")
                                ),
                                fecha_creacion = rs.getString("fecha_creacion"),
                                items = items_lote,
                                fecha_vencimiento = rs.getString("fecha_vencimiento"),
                            )
                        )
                    }
                }
            }
        }
        return lote
    }

    fun existsById(id: String): Boolean {
        val sql = "SELECT 1 FROM lote WHERE id = ? LIMIT 1"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.executeQuery().use { rs ->
                    rs.next()
                }
            }
        }
    }

    private fun create_items(items: Set<ItemLote>, id_lote: String){
        items.forEach { i ->
            var id = i.id.toString()

            val existe = existsById(i.id.toString())

            if(existe){
                do{
                    id = UUID.randomUUID().toString()
                }while (i.id.toString().equals(id))
            }

            val sql = "INSERT INTO item_lote (id, nombre, precio, id_lote) VALUES (?, ?, ?, ?)"

            db_service.connect().use { conn ->
                conn.prepareStatement(sql).use { pstmt ->
                    pstmt.setString(1, id)
                    pstmt.setString(2, i.nombre)
                    pstmt.setInt(3, i.precio)
                    pstmt.setString(4, id_lote)
                    pstmt.executeUpdate()
                }
            }

        }
    }

    private fun find_items_by_lote_id(id_lote: String): Set<ItemLote>{
        val items = mutableSetOf<ItemLote>()
        val sql = "SELECT * FROM item_lote WHERE id_lote = ?"

        db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id_lote)
                pstmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        items.add(ItemLote(
                            id = UUID.fromString(rs.getString("id")),
                            nombre = rs.getString("nombre"),
                            precio = rs.getInt("precio"),
                        ))
                    }
                }
            }
        }

        return items
    }

    private fun exist_item_by_id(id: String){
        val sql = "SELECT 1 FROM item_lote WHERE id = ? LIMIT 1"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, id)
                pstmt.executeQuery().use { rs ->
                    rs.next()
                }
            }
        }
    }

    private fun get_initial_state(): String{
        val sql = "SELECT id FROM estado_lote WHERE nombre = \"pendiente\" LIMIT 1"

        return db_service.connect().use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.executeQuery().use { rs ->
                    rs.getString("id")
                }
            }
        }
    }
}