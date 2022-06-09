package fragments


import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_buscar_fragment.*
import kotlinx.android.synthetic.main.activity_inicio_fragment.*
import models.Pieza
import recycler.PiezaAdapter
import recycler.PiezaInicioAdapter

class BuscarFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()
    protected var listaBuscadorPiezas: ArrayList<Pieza> = ArrayList<Pieza>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_buscar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnBuscar.setOnClickListener {
            getBuscadorPiezas()
        }

    }

    override fun onResume() {
        super.onResume()
    }


    private fun getBuscadorPiezas() {
        var filtroBD : String ?= null
        var em : String
        if (spFiltro.selectedItem.toString() == "Marca") {
            filtroBD = "Brand"
        }else{
            filtroBD = spFiltro.selectedItem.toString()
        }
        Toast.makeText(this.requireContext(), filtroBD+"", Toast.LENGTH_SHORT).show()
        listaBuscadorPiezas = arrayListOf<Pieza>()
        listaBuscadorPiezas.clear()
        val docPieza = db.collection("piezas").orderBy(filtroBD).whereEqualTo("Vendido", false)
           // .whereEqualTo(filtroBD,txtFiltro.toString())
//            docPieza.whereNotEqualTo("Email", thisUser).orderBy("Email")


        docPieza.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var pieza: Pieza = Pieza()
                    pieza.Nombre = document.data.getValue("Nombre").toString()
                    pieza.Marca = document.data.getValue("Brand").toString()
                    pieza.Modelo = document.data.getValue("Modelo").toString()
                    pieza.Descripcion = document.data.getValue("Descripcion").toString()
                    pieza.Precio = document.data.getValue("Price").toString().toDouble()
                    em = document.data.getValue("Email").toString()
                    if (em != thisUser){
                        listaBuscadorPiezas.add(pieza)
                    }

                }

            }.addOnCompleteListener {
                Toast.makeText(this.context, " "+listaBuscadorPiezas.size, Toast.LENGTH_SHORT).show()
                var adapter = PiezaInicioAdapter(context as FragmentActivity, listaBuscadorPiezas)
                recyclerBuscador.adapter = adapter
                recyclerBuscador.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()
            }
    }

}