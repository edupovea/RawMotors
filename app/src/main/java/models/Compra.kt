package models

import java.io.Serializable
import java.util.*

class Compra(var direccion: String?=null, var fecha: Date?=null, var comprador: String?=null, var pieza: Pieza?): Serializable {

    override fun toString(): String {
        return "Compra(direccion=$direccion, fecha=$fecha, comprador=$comprador, pieza=$pieza)"
    }
}