package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.activity_detalle.*
import models.Pieza

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        recibirDatos()

        btnComprar.setOnClickListener {
            Toast.makeText(this, "trying to buy", Toast.LENGTH_SHORT).show()
        }

    }


    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        val pieza : Pieza = intent?.getSerializable("infoPieza") as Pieza
        dtlNom.setText(pieza.Nombre)
        dtlPrice.setText(pieza.Precio.toString()+"€")
        dtlDesc.setText(pieza.Descripcion)
        dtlBrand.setText(pieza.Marca)
        dtlModel.setText(pieza.Modelo)
    }
}