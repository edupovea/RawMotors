package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_detalle.*
import models.Persona
import models.Pieza

class DetalleActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        recibirDatos()

        btnComprar.setOnClickListener {
            val cambiarPantalla = Intent(this, ComprarActivity::class.java)
            startActivity(cambiarPantalla)
        }

    }


    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        val pieza : Pieza = intent?.getSerializable("infoDetalle") as Pieza
        dtlNom.setText(pieza.Nombre)
        dtlPrice.setText(pieza.Precio.toString()+"€")
        dtlDesc.setText(pieza.Descripcion)
        dtlBrand.setText(pieza.Marca)
        dtlModel.setText(pieza.Modelo)

    }


}