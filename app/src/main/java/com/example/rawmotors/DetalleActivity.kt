package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_detalle.*
import models.Persona
import models.Pieza
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class DetalleActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance();
    var prec: Double?=null
    var pzaVendida : Pieza? = Pieza()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        recibirDatos()
        btnComprar.setOnClickListener {
            enviarDatos()
        }
    }

    private fun enviarDatos(){
        val cambiarPantalla = Intent(this@DetalleActivity, ComprarActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("pzaVendida", pzaVendida)
        Toast.makeText(this, pzaVendida.toString(), Toast.LENGTH_SHORT).show()
        intent.putExtras(bundle)
        startActivity(cambiarPantalla)
    }


    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        pzaVendida  = intent?.getSerializable("infoDetalle") as Pieza?
        dtlNom.setText(pzaVendida?.Nombre)
        prec = pzaVendida?.Precio
        dtlPrice.setText(prec.toString()+"€")
        dtlDesc.setText(pzaVendida?.Descripcion)
        dtlBrand.setText(pzaVendida?.Marca)
        dtlModel.setText(pzaVendida?.Modelo)

    }


}