package recycler

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.example.rawmotors.AddPiezaActivity
import com.example.rawmotors.EditPiezaActivity
import com.example.rawmotors.R
import com.google.firebase.firestore.FirebaseFirestore
import models.Pieza
import kotlin.random.Random


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
            val intent: Intent = Intent(contexto, EditPiezaActivity::class.java)
            var pzaEdit : Pieza = piezas[position]
            val bundle = Bundle()
            bundle.putSerializable("infoPieza", pzaEdit)
            intent.putExtras(bundle)
            contexto.startActivity(intent)
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