package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.activity_comprar.*
import kotlinx.android.synthetic.main.activity_datos_compra.*
import kotlinx.android.synthetic.main.activity_edit_pieza.*
import models.Compra
import models.Pieza
import java.util.*

class DatosCompraActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()

    //var piezaprueba : Pieza = Pieza("Coche", "BMW","X6",123.23,"Nuevo",null,false)
    //var direccion = "Calle Playa Virginia 1, 29018, Malaga"
    //var compraprueba : Compra = Compra(direccion, Date(),auth.currentUser.toString(),piezaprueba)
    lateinit var informacionCompra : Compra
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_compra)
        recibirDatos()
        btnCont.setOnClickListener {
            if (compForm()){
                terminarCompra()
            }

        }


    }


    private fun compForm(): Boolean {
        if (txtNomTitular.text.isNullOrEmpty() || txtApellidoTitular.text.isNullOrEmpty() || txtNumTarjeta.text.isNullOrEmpty() || txtFechaCad.text.isNullOrEmpty() || txtCVV.text.isNullOrEmpty()) {
            showAlert(R.string.camposRegistroNoRellenos)
            return false
        }
        return true
    }

    private fun showAlert(msj: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(msj)
        builder.setPositiveButton(R.string.reintentar, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    //Recibimos los datos de contacto del comprador para efectuar la compra
    private fun recibirDatos(){
        if (intent.getSerializableExtra("infoCompra") != null) {
            informacionCompra = intent.getSerializableExtra("infoCompra") as Compra
        }
    }


    //Terminamos la compra añadiendo a la coleccion compras y seteamos a true nuestro campo vendido de la coleccion piezas
    fun terminarCompra(){

        db.collection("compras").document(txtNomTitular.text.toString()+" "+informacionCompra.pieza?.Nombre).set(
            hashMapOf(
                "Nombre titular" to txtNomTitular.text.toString(),
                "Appelidos" to txtApellidoTitular.text.toString(),
                "Num. Tarjeta" to txtNumTarjeta.text.toString(),
                "Fecha Cad." to txtFechaCad.text.toString(),
                "Direccion" to informacionCompra.direccion.toString(),
                "Fecha Compra" to informacionCompra.fecha.toString(),
                "Comprador" to thisUser,
                "Nombre Pieza" to informacionCompra.pieza?.Nombre.toString(),
                "Pieza" to informacionCompra.pieza.toString()


            )
        )
        val act = db.collection("piezas")
            .document(informacionCompra.pieza?.Nombre + " " + informacionCompra.pieza?.Marca)
        act.update(
            hashMapOf(

                "Vendido" to true

            ) as Map<String, Any>
        ).addOnCompleteListener {
            if (it.isSuccessful){

                showAlert(R.string.piezaComprada, R.string.exitoso, R.string.continuar)
                val intent: Intent =
                    Intent(
                        this@DatosCompraActivity,
                        InfladorActivity::class.java
                    )
                startActivity(intent)

            }else{
                showAlert(R.string.error, R.string.error, R.string.error)

            }
        }
    }

    private fun showAlert(msj: Int, title : Int, btn : Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msj)
        builder.setPositiveButton(btn, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}