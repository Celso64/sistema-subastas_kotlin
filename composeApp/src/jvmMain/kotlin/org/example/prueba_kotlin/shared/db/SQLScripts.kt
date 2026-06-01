package org.example.prueba_kotlin.shared.db

object SQLScripts {
    val creates: List<String> = listOf(
        """
        CREATE TABLE IF NOT EXISTS comprador (
            id TEXT PRIMARY KEY,
            nombre TEXT NOT NULL UNIQUE,
            contacto TEXT UNIQUE,
            fecha_creacion TEXT NOT NULL,
            imagen_nombre TEXT
        );
        """.trimIndent(),
        """
        CREATE TABLE IF NOT EXISTS estado_lote (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL
        );
        """.trimIndent(),
        """
         CREATE TABLE IF NOT EXISTS lote (
            id TEXT PRIMARY KEY,
            descripcion TEXT,
            fecha_creacion TEXT NOT NULL,
            fecha_vencimiento TEXT NOT NULL,
            id_comprador TEXT NOT NULL,
            id_estado TEXT NOT NULL,
            FOREIGN KEY (id_comprador) REFERENCES comprador (id),
            FOREIGN KEY (id_estado) REFERENCES estado_lote (id)
        );  
        """.trimIndent(),
        """
        CREATE TABLE IF NOT EXISTS item_lote (
            id TEXT PRIMARY KEY,
            nombre TEXT NOT NULL,
            precio INTEGER NOT NULL,
            id_lote TEXT NOT NULL,
            FOREIGN KEY (id_lote) REFERENCES lote (id)
        );
        """.trimIndent()
    )

    val inserts = listOf<String>(
        """
        INSERT INTO estado_lote ("nombre")
        SELECT "pendiente"
        WHERE NOT EXISTS(
            SELECT 1 
            FROM estado_lote
            WHERE nombre == "pendiente"
        );
        """.trimIndent(),
        """
        INSERT INTO estado_lote ("nombre")
        SELECT "pagado"
        WHERE NOT EXISTS(
            SELECT 1 
            FROM estado_lote
            WHERE nombre == "pagado"
        );
        """.trimIndent(),
    )
}