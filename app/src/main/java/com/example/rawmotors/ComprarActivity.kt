package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_comprar.*

class ComprarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)

        btnContinuarcompra.setOnClickListener {
            if (compForm()){
                var direccion : String ?=null
                direccion = spinnerTipo.selectedItem.toString()+txtCalle.text.toString()+txtNumCalle.text.toString()+
                ", "+txtCodPostal.text.toString()+", "+txtCiudad.text.toString()
                Toast.makeText(this, direccion, Toast.LENGTH_SHORT).show()
                val intent: Intent =Intent(this@ComprarActivity,RegistroActivity::class.java)
                val b: Bundle
                startActivity(intent)
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
}