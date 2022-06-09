package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_comprar.*
import models.Compra
import models.Pieza
import java.util.*

class ComprarActivity : AppCompatActivity() {
    val auth by lazy { FirebaseAuth.getInstance() }
    var pVendida : Pieza? = Pieza()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)
        recibirDatos()
        btnContinuarcompra.setOnClickListener {
            if (compForm()){
                enviarDatos()
            }
        }

    }

    private fun compForm(): Boolean{
        if (txtCalle.text.isNullOrEmpty() || txtNumCalle.text.isNullOrEmpty() || txtCodPostal.text.isNullOrEmpty() ||txtCiudad.text.isNullOrEmpty() || txtTlf.text.isNullOrEmpty()){
            showAlert(R.string.camposRegistroNoRellenos)
            return false
        }
        return true
    }

    private fun showAlert(msj: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login Fallido")
        builder.setMessage(msj)
        builder.setPositiveButton("Reintentar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun enviarDatos(){
        var direccion : String ?=null
        var fecha = Date()
        var comprador = auth.currentUser.toString()
        direccion = spinnerTipo.selectedItem.toString()+txtCalle.text.toString()+txtNumCalle.text.toString()+
                ", "+txtCodPostal.text.toString()+", "+txtCiudad.text.toString()
        val cambiarPantalla = Intent(this, DatosCompraActivity::class.java)



        var infoCompra : Compra = Compra(direccion, fecha, comprador, pVendida!!)
        val bundle: Bundle = Bundle()

        bundle.putSerializable("infoCompra",infoCompra)
        Toast.makeText(this, infoCompra.toString(), Toast.LENGTH_SHORT).show()
        intent.putExtras(bundle)
        startActivity(cambiarPantalla)
    }

    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        pVendida   = intent?.getSerializable("pVendida") as Pieza?
        Toast.makeText(this, pVendida.toString(), Toast.LENGTH_SHORT).show()

    }
}