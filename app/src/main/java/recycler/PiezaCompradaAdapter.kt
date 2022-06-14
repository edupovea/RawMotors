package recycler

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rawmotors.R
import models.Pieza

class PiezaCompradaAdapter(val contexto: AppCompatActivity, val pieza : ArrayList<Pieza>): RecyclerView.Adapter<PiezaViewHolder>() {
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
        holder.nombre.text = pieza[position].Nombre.toString()
        holder.marca.text = pieza[position].Marca.toString()
        holder.modelo.text = pieza[position].Modelo.toString()
        holder.precio.text = pieza[position].Precio.toString() + R.string.euro

    }

    override fun getItemCount(): Int {
        return pieza.size
    }


}