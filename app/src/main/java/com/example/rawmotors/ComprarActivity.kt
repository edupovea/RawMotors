package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_comprar.*
import models.Compra
import models.Pieza
import java.util.*

class ComprarActivity : AppCompatActivity() {

    val auth by lazy { FirebaseAuth.getInstance() }
    var pVendida: Pieza? = Pieza()
    val thisUser = auth.currentUser?.email.toString()

    lateinit var infoCompra: Compra

   // var piezaprueba: Pieza = Pieza("Coche", "BMW", "X6", 123.23, "Nuevo", false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)
        recibirDatos()
        btnContinuarcompra.setOnClickListener {
            if (compForm()) {
                enviarDatos()
            }
        }

    }

    //Comprobamos el form para que todos los campos esten rellenoss
    private fun compForm(): Boolean {
        if (txtCalle.text.isNullOrEmpty() || txtNumCalle.text.isNullOrEmpty() || txtCodPostal.text.isNullOrEmpty() || txtCiudad.text.isNullOrEmpty() || txtTlf.text.isNullOrEmpty()) {
            showAlert(R.string.camposRegistroNoRellenos)
            return false
        }
        return true
    }
    //Cogemos los datos del formulario si todos los campos estan rellenos
    private fun getFormData() {
        var direccion: String? = null
        var fecha = Date()
        var comprador = thisUser
        direccion =
            spinnerTipo.selectedItem.toString() +" "+ txtCalle.text.toString() +" "+ txtNumCalle.text.toString() +
                    ", " + txtCodPostal.text.toString() + ", " + txtCiudad.text.toString()
        infoCompra = Compra(direccion, fecha, comprador, pVendida)

    }
    //Enviamos los datos del formulario a la siguiente pantalla para terminar la compra
    private fun enviarDatos() {
        getFormData()
        val cambiarPantalla = Intent(this, DatosCompraActivity::class.java)
        cambiarPantalla.putExtra("infoCompra", infoCompra)
        startActivity(cambiarPantalla)
    }

    private fun showAlert(msj: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(msj)
        builder.setPositiveButton(R.string.reintentar, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Recibimos los datos de la pantalla de detalle para empezar la compra
    private fun recibirDatos() {
        if (intent.getSerializableExtra("pzaVendida") != null) {
            pVendida = intent.getSerializableExtra("pzaVendida") as Pieza?
        }
    }
}