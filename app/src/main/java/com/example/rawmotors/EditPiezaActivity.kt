package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.activity_add_pieza.txtDesc
import kotlinx.android.synthetic.main.activity_add_pieza.txtModel
import kotlinx.android.synthetic.main.activity_add_pieza.txtPrice
import kotlinx.android.synthetic.main.activity_edit_pieza.*
import kotlinx.android.synthetic.main.activity_profile_fragment.*
import models.Pieza
import recycler.PiezaAdapter

class EditPiezaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pieza)
        recibirDatos()

        btnEdit.setOnClickListener {
            if (editPieza()){
                showAlert(R.string.piezaInsertada, R.string.exito, R.string.continuar)
            }else{
                showAlert(R.string.piezaNoInsertada, R.string.error, R.string.reintentar)
            }
        }
    }

    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        val pieza : Pieza = intent?.getSerializable("infoPieza") as Pieza
        lblNom.setText(pieza.Nombre)
        txtEditPrice.setText(pieza.Precio.toString())
        txtEditDesc.setText(pieza.Descripcion)
        lblBrand.setText(pieza.Marca.toString())
        txtEditModel.setText(pieza.Modelo)
    }

    fun editPieza(): Boolean{
        recibirDatos()
        val intent : Bundle? = this.intent.extras
        val pieza : Pieza = intent?.getSerializable("infoPieza") as Pieza
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val thisUser = auth.currentUser?.email.toString()

        db.collection("piezas").document(lblNom.text.toString()+" "+lblBrand.text.toString()).set(
            hashMapOf(
                "Email" to thisUser,
                "Nombre" to lblNom.text.toString(),
                "Brand" to lblBrand.text.toString(),
                "Modelo" to txtEditModel.text.toString(),
                "Descripcion" to txtEditDesc.text.toString(),
                "Price" to txtEditPrice.text.toString().toDouble(),
                "Vendido" to pieza.Vendido
            )
        )
        return true
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