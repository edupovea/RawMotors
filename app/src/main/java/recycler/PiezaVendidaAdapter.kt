package recycler

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.EditPiezaActivity
import com.example.rawmotors.R
import com.google.firebase.firestore.FirebaseFirestore
import models.Pieza


class PiezaVendidaAdapter (val contexto: AppCompatActivity, val piezas : ArrayList<Pieza>):RecyclerView.Adapter<PiezaViewHolder>() {

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

    }

    override fun getItemCount(): Int {
        return piezas.size
    }


}