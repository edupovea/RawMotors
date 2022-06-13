package models

import android.widget.ImageView
import java.io.Serializable

class Pieza(var Nombre: String?=null,var Marca: String?=null, var Modelo: String?=null, var Precio: Double?=null, var Descripcion: String? = null, var Foto: ImageView?=null, var Vendido: Boolean?=null) : Serializable {

    override fun toString(): String {
        return "Pieza(Nombre=$Nombre, Marca=$Marca, Modelo=$Modelo, Precio=$Precio, Descripcion=$Descripcion, Vendido=$Vendido)"
    }
}