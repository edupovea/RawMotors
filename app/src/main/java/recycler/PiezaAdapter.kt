package recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rawmotors.InfladorActivity
import com.example.rawmotors.R
import models.Pieza

class PiezaAdapter (val contexto: InfladorActivity, val piezas : ArrayList<Pieza>):RecyclerView.Adapter<PiezaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiezaViewHolder {
        return PiezaViewHolder(contexto.layoutInflater.inflate(R.layout.item_recycler_pieza, parent, false))
    }

    override fun onBindViewHolder(holder: PiezaViewHolder, position: Int) {
        holder.nombre.text = piezas[position].Nombre.toString()
        holder.marca.text = piezas[position].Marca.toString()
        holder.modelo.text = piezas[position].Modelo.toString()
        holder.precio.text = piezas[position].Precio.toString()


    }

    override fun getItemCount(): Int {
       return piezas.size
    }
}