package fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rawmotors.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_inicio_fragment.*
import kotlinx.android.synthetic.main.activity_profile_fragment.*
import models.Pieza
import recycler.PiezaAdapter
import recycler.PiezaInicioAdapter
import java.util.*
import kotlin.collections.ArrayList

class InicioFragment() : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()

    protected var listaInicioPiezas: ArrayList<Pieza> = ArrayList<Pieza>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_inicio_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onResume() {
        super.onResume()
        getInicioPiezas()

    }

    private fun getInicioPiezas() {
        listaInicioPiezas = arrayListOf<Pieza>()
        listaInicioPiezas.clear()
        val docPieza = db.collection("piezas").whereNotEqualTo("Email", thisUser)
        docPieza.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var pieza: Pieza = Pieza()
                    pieza.Nombre = document.data.getValue("Nombre").toString()
                    pieza.Marca = document.data.getValue("Brand").toString()
                    pieza.Modelo = document.data.getValue("Modelo").toString()
                    pieza.Descripcion = document.data.getValue("Descripcion").toString()
                    pieza.Precio = document.data.getValue("Price").toString().toDouble()
                    listaInicioPiezas.add(pieza)
                }

            }.addOnCompleteListener {
                var adapter = PiezaInicioAdapter(context as FragmentActivity, listaInicioPiezas)
                recyclerInicio.adapter = adapter
                recyclerInicio.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()
            }
    }
}