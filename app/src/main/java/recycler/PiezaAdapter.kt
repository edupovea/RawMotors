package recycler

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fragments.InicioFragment
import fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_inflador.*
import models.Persona
import models.Pieza

class PiezaAdapter (val contexto: FragmentActivity, val piezas : ArrayList<Pieza>):RecyclerView.Adapter<PiezaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiezaViewHolder {
        return PiezaViewHolder(
            contexto.layoutInflater.inflate(
                R.layout.item_recycler_pieza,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PiezaViewHolder, position: Int) {
        holder.nombre.text = piezas[position].Nombre.toString()
        holder.marca.text = piezas[position].Marca.toString()
        holder.modelo.text = piezas[position].Modelo.toString()
        holder.precio.text = piezas[position].Precio.toString() + "€"

        holder.foto.setOnClickListener {
            val intent: Intent =
                Intent(
                    contexto,
                    AddPiezaActivity::class.java
                )
            contexto.startActivity(intent)
        }

        val db = FirebaseFirestore.getInstance()
        val auth by lazy { FirebaseAuth.getInstance() }
        val thisUser = auth.currentUser?.email.toString()

        val doc = db.collection("piezas")
        doc.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var persona: Persona = Persona()
                    persona.email = document.data.getValue("Email").toString()
                    if (persona.email == thisUser) {
                        holder.btnBorrar.visibility = View.VISIBLE
                    }
                }
            }

        holder.btnBorrar.setOnClickListener {
            FirebaseFirestore.getInstance().collection("piezas").document(
                holder.nombre.text.toString() + " " +
                        holder.marca.text.toString()
            ).delete()
            piezas.removeAt(position)

        }


    }

    override fun getItemCount(): Int {
        return piezas.size
    }


}