package recycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.DetalleActivity
import com.example.rawmotors.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fragments.InicioFragment
import fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_inflador.*
import models.Persona
import models.Pieza

class PiezaInicioAdapter (val contexto: FragmentActivity, val piezas : ArrayList<Pieza>):RecyclerView.Adapter<PiezaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiezaViewHolder {
        return PiezaViewHolder(
            contexto.layoutInflater.inflate(
                R.layout.item_recycler_pieza_inicio,
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
            val intent: Intent = Intent(contexto, DetalleActivity::class.java)
            var pzaEdit : Pieza = piezas[position]
            val bundle = Bundle()
            bundle.putSerializable("infoPieza", pzaEdit)
            intent.putExtras(bundle)
            contexto.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return piezas.size
    }


}