package recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rawmotors.R

class PiezaViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nombre: TextView by lazy { itemView.findViewById(R.id.txtNombre)}
    val marca: TextView by lazy { itemView.findViewById(R.id.txtMarca)}
    val modelo: TextView by lazy { itemView.findViewById(R.id.txtModelo)}
    val precio: TextView by lazy { itemView.findViewById(R.id.txtPrecio)}
    val foto: ImageView by lazy { itemView.findViewById(R.id.foto)}
    val btnBorrar: ImageView by lazy { itemView.findViewById(R.id.imgDelete)}


}