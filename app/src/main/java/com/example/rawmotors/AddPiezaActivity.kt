package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_pieza.*
import kotlinx.android.synthetic.main.activity_inicio_fragment.*
import models.Pieza
import recycler.PiezaAdapter

class AddPiezaActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    protected lateinit var  lista: RecyclerView
    protected lateinit var adapter: PiezaAdapter
    protected var listaPiezas: ArrayList<Pieza> =ArrayList<Pieza>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pieza)

        btnAdd.setOnClickListener {
            if (!txtNom.text.isBlank() && !txtBrand.text.isBlank() && !txtModel.text.isBlank() && !txtDesc.text.isBlank() && !txtPrice.text.isBlank()) {
                if (addPieza()){
                    Toast.makeText(this, "Información guardada correctamente", Toast.LENGTH_SHORT).show()
                    consultarBD()

                }else{
                    Toast.makeText(this, "No ha sido posible guardar la información correctamente", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, R.string.camposRegistroNoRellenos, Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun addPieza(): Boolean{
        val thisUser = auth.currentUser?.email.toString()

        db.collection("piezas").document(txtNom.text.toString()+" "+txtBrand.text.toString()).set(
            hashMapOf(
                "Email" to thisUser,
                "Nombre" to txtNom.text.toString(),
                "Brand" to txtBrand.text.toString(),
                "Modelo" to txtModel.text.toString(),
                "Descripcion" to txtDesc.text.toString(),
                "Price" to txtPrice.text.toString().toDouble()

            )
        )

        return true


    }

    fun consultarBD(){
        var adapter = PiezaAdapter(this, listaPiezas)
        recyclerInicio.adapter = adapter
        recyclerInicio.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
        }
    }









