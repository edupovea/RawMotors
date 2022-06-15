package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comprado.*
import kotlinx.android.synthetic.main.activity_sold_item.*
import models.Compra
import models.Pieza
import recycler.PiezaCompradaAdapter
import recycler.PiezaVendidaAdapter
import kotlin.math.roundToLong

class CompradoActivity : AppCompatActivity() {
    private lateinit var compradoArraylist: ArrayList<Pieza>
    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprado)
        getPiezasCompradas()
        recyclerComprado.apply {
            setHasFixedSize(true)
            var itemDecoration = DividerItemDecoration(this@CompradoActivity, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(this@CompradoActivity, R.drawable.divider)?.let {
                itemDecoration.setDrawable(
                    it
                )
            }
            addItemDecoration(itemDecoration)
        }
    }



    //Funcion que muestra las piezas que nosotros como usuarios hemos adquirido desde la app
    private fun getPiezasCompradas() {
        compradoArraylist = arrayListOf<Pieza>()
        var arrayNombrePiezas = arrayListOf<String>()
        compradoArraylist.clear()
        val docCompra = db.collection("compras").whereEqualTo("Comprador", thisUser)
        docCompra.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //Sacamos el nombre de la pieza desde nuestra coleccion piezas
                    var nombre: String
                    nombre = document.data.getValue("Nombre Pieza").toString()
                    //Guardamos los nombres en un array de String
                    arrayNombrePiezas.add(nombre)
                }
            }.addOnCompleteListener {

                for (i in arrayNombrePiezas.indices){
                    //Y recorremos el array de nombres de piezas en nuestra coleccion piezas para rellenar el adapter
                    val docPieza = db.collection("piezas").whereEqualTo("Nombre", arrayNombrePiezas[i])
                    docPieza.get().addOnSuccessListener { result ->
                        for (document in result){
                            var pieza: Pieza = Pieza()
                            pieza.Nombre = document.data.getValue("Nombre").toString()
                            pieza.Marca = document.data.getValue("Brand").toString()
                            pieza.Modelo = document.data.getValue("Modelo").toString()
                            pieza.Descripcion = document.data.getValue("Descripcion").toString()
                            pieza.Precio =  document.data.getValue("Price").toString().toDouble()
                            compradoArraylist.add(pieza)
                        }

                    }.addOnCompleteListener {
                        //Rellenamos el adapter y lo actualizamos
                        var adapterComprado = PiezaCompradaAdapter(this@CompradoActivity, compradoArraylist)
                        recyclerComprado.adapter = adapterComprado
                        recyclerComprado.layoutManager = LinearLayoutManager(this)
                        adapterComprado.notifyDataSetChanged()
                    }
                }
            }


    }

}