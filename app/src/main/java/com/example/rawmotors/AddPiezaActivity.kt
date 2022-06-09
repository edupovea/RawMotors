package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.activity_inicio_fragment.*
import kotlinx.android.synthetic.main.item_recycler_pieza.*
import models.Pieza
import recycler.PiezaAdapter

class AddPiezaActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pieza)
        recibirDatos()

        btnAdd.setOnClickListener {
            if (!txtNom.text?.isNullOrEmpty()!! && !txtBrand.text?.isNullOrEmpty()!! && !txtModel.text?.isNullOrEmpty()!! && !txtDesc.text?.isNullOrEmpty()!! && !txtPrice.text?.isNullOrEmpty()!!) {
                if (addPieza()){
                    Toast.makeText(this, "Información guardada correctamente", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this, "No ha sido posible guardar la información correctamente", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, R.string.camposRegistroNoRellenos, Toast.LENGTH_SHORT).show()
            }

        }
        btnImage.setOnClickListener {
           //abrir carpeta de imgs para que seleccione icono el usuario
        }

    }

    fun addPieza(): Boolean{
        val thisUser = auth.currentUser?.email.toString()
        val vendido : Boolean = false

        db.collection("piezas").document(txtNom.text.toString()+" "+txtBrand.text.toString()).set(
            hashMapOf(
                "Email" to thisUser,
                "Nombre" to txtNom.text.toString(),
                "Brand" to txtBrand.text.toString(),
                "Modelo" to txtModel.text.toString(),
                "Descripcion" to txtDesc.text.toString(),
                "Price" to txtPrice.text.toString().toDouble(),
                "Vendido" to vendido
            )
        )

        return true


    }

    private fun recibirDatos(){
        val intent : Bundle? = this.intent.extras
        val pieza : Pieza? = intent?.getSerializable("infoPieza") as Pieza?
        Toast.makeText(this, pieza?.Precio.toString(), Toast.LENGTH_SHORT).show()
        if (pieza == null){
            txtPrice.setText("")
        }
        txtNom.setText(pieza?.Nombre)
        txtPrice.setText(pieza?.Precio.toString())
        txtDesc.setText(pieza?.Descripcion)
        txtBrand.setText(pieza?.Marca)
        txtModel.setText(pieza?.Modelo)
    }

    }











