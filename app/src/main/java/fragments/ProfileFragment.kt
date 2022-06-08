package fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_inflador.*
import kotlinx.android.synthetic.main.activity_profile_fragment.*
import kotlinx.android.synthetic.main.item_recycler_pieza.*
import models.Persona
import models.Pieza
import recycler.PiezaAdapter

class ProfileFragment : Fragment() {

    private lateinit var profileArrayList: ArrayList<Pieza>
    private val db = FirebaseFirestore.getInstance()
    val auth by lazy { FirebaseAuth.getInstance() }
    val thisUser = auth.currentUser?.email.toString()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_profile_fragment, container, false)



    }

    override fun onResume() {
        super.onResume()
        getProfilePiezas()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var comp = false
        while (comp == false){
            if (getNombre()){
                comp = true
            }
        }


        btnAddPieza.setOnClickListener {
            val cambiarPantalla = Intent(context, AddPiezaActivity::class.java)
            startActivity(cambiarPantalla)
        }

        btnVendido.setOnClickListener {
            val cambiarPantalla = Intent(context, AddPiezaActivity::class.java)
            startActivity(cambiarPantalla)
        }

    }

    fun getNombre(): Boolean {
        var uname : String ?= null
        val username = db.collection("users").whereEqualTo("Email", thisUser)
        username.get().addOnSuccessListener { resulta ->
            for (documento in resulta){
                var persona : Persona = Persona()
                persona.username = documento.data.getValue("Usuario").toString()
                uname = persona.username!!
                txtUsuario.setText("@"+uname.toString())

            }
        }
        return true
    }
     private fun getProfilePiezas() {

        profileArrayList = arrayListOf<Pieza>()
         profileArrayList.clear()
        val docPieza = db.collection("piezas").whereEqualTo("Email", thisUser)
            .whereEqualTo("Vendido",false)
        docPieza.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var pieza: Pieza = Pieza()
                    pieza.Nombre = document.data.getValue("Nombre").toString()
                    pieza.Marca = document.data.getValue("Brand").toString()
                    pieza.Modelo = document.data.getValue("Modelo").toString()
                    pieza.Descripcion = document.data.getValue("Descripcion").toString()
                    pieza.Precio =  document.data.getValue("Price").toString().toDouble()
                    profileArrayList.add(pieza)
                }
            }.addOnCompleteListener {
                var adapter = PiezaAdapter(context as FragmentActivity, profileArrayList)
                recyclerPiezas.adapter = adapter
                recyclerPiezas.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()
            }


    }


}