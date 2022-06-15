package com.example.rawmotors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile_fragment.*
import kotlinx.android.synthetic.main.activity_sold_item.*
import models.Pieza
import recycler.PiezaAdapter
import recycler.PiezaInicioAdapter
import recycler.PiezaVendidaAdapter

class SoldItemActivity : AppCompatActivity() {
    private lateinit var vendidasArraylist: ArrayList<Pieza>
    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sold_item)
        recyclerVendido.apply {
            setHasFixedSize(true)
            var itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(context as FragmentActivity, R.drawable.divider)?.let {
                itemDecoration.setDrawable(
                    it
                )
            }
            addItemDecoration(itemDecoration)
        }
    }

    override fun onResume() {
        super.onResume()
        getPiezasVendidas()
    }


    private fun getPiezasVendidas() {

        vendidasArraylist = arrayListOf<Pieza>()
        vendidasArraylist.clear()
        val docPieza = db.collection("piezas").whereEqualTo("Email", thisUser).whereEqualTo("Vendido", true)
        docPieza.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var pieza: Pieza = Pieza()
                    pieza.Nombre = document.data.getValue("Nombre").toString()
                    pieza.Marca = document.data.getValue("Brand").toString()
                    pieza.Modelo = document.data.getValue("Modelo").toString()
                    pieza.Descripcion = document.data.getValue("Descripcion").toString()
                    pieza.Precio =  document.data.getValue("Price").toString().toDouble()
                    vendidasArraylist.add(pieza)
                }
            }.addOnCompleteListener {
                //Rellenamos el adapter y le pasamos el array de piezas vendidas
                var adapterSold = PiezaVendidaAdapter(this@SoldItemActivity, vendidasArraylist)
                recyclerVendido.adapter = adapterSold
                recyclerVendido.layoutManager = LinearLayoutManager(this)
                adapterSold.notifyDataSetChanged()
            }


    }
}